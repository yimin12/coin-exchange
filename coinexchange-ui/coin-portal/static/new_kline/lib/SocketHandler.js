function SocketHandler(url) {
  // 基于H5原生api
  this.ws = new WebSocket(url);
  // 定义一个信号发射塔，用于发送事件
  this.tower = document.createElement('div');
}

SocketHandler.prototype = {
  // 订阅频道
  channels : {},
  // 反应堆（用于收集和分发socket的响应）
  reactions : {},
  // 缓存监听事件
  events : [],
  // 获取当前时间
  nowTime : function() {
    return new Date().getTime();
  },
  // 打开socket连接
  open : function(heartbeatTimeout) {
    this.ws.onopen = function(){
      var heartbeatSendInterval = heartbeatTimeout / 2;
      this.lastSubscribeTime = this.nowTime();
      this.pingIntervalId = setInterval(function(){
        var iv = this.nowTime() - this.lastSubscribeTime;
        // 超过一定时间自动与后台ping、pong  单位：秒
        if ((heartbeatSendInterval + iv) >= heartbeatTimeout) {
          this.send('ping');
        }
      }.bind(this), heartbeatSendInterval);
    }.bind(this);
  },

  // 创建自定义事件
  createEvent : function(event, detail){
    var evt = document.createEvent('CustomEvent');
    evt.initCustomEvent(event, false, false, detail);
    return evt;
  },

  // 打开socket连接
  connect: function(heartbeatTimeout) {
    this.open(heartbeatTimeout);
    this.message();
    this.close();
  },

  // 订阅消息 ch 为订阅的频道 id 为订阅唯一标识
  subscribe : function(ch, id, token) {
    if(this.ws.readyState == 1) {
      var obj = {};
      if(ch) {
        obj.sub = ch;
        obj.id = id;
        if(token) obj.authorization = token;
        if(this.channels.hasOwnProperty(id)) {
          this.unsubscribe.apply(this, Object.values(this.channels[id]));
        }
        this.channels[id] = obj;
        this.reactions[ch] = this.createEvent(id);
        this.send(obj);
      }
    }
  },

  // 监听订阅结果
  on : function(id, callback) {
    var handler = function(e) {
      if(callback) callback(e.data, e);
    };
    this.tower.addEventListener(id, handler);
    var key = id + '_' + this.nowTime();
    this.events.push({key : key, handler: handler});
  },

  // 取消订阅
  unsubscribe : function(ch, id, token) {
    if(this.ws.readyState == 1) {
      var obj = {};
      if(ch) {
        obj.cancel = ch;
        obj.id = id;
        if(token) obj.authorization = token;
        if(this.channels.hasOwnProperty(id)) {
          delete this.channels[id];
          delete this.reactions[ch];
        }
        this.events = this.events.filter(function(v){
          if(v.key.indexOf(id) !== -1) {
            // 失效ID解除监听
            this.tower.removeEventListener(id, v.handler);
          }else{
            return v;
          }
        }.bind(this));
        this.send(obj);
      }
    }
  },

  send : function(data){
    if(typeof data === 'object') {
      data = JSON.stringify(data);
    }
    this.ws.send(data);
  },

  message : function() {
    this.ws.onmessage = function(evt){
      var data = evt.data;
      this.lastSubscribeTime = this.nowTime();
      if(data) {
        if(data !== 'pong') data = JSON.parse(data);
        if(typeof data === 'object' && data.hasOwnProperty('ch')) {
          var e = this.reactions[data.ch];
          if(e) {
            e.data = data;
            this.tower.dispatchEvent(e);
          }
        }
      }
    }.bind(this);
  },

  close : function(callback) {
    this.ws.onclose = function(evt){
      clearInterval(this.pingIntervalId);
      if(callback) callback(evt, this.ws);
    }.bind(this);
  }
};
