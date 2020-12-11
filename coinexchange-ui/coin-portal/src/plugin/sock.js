/**
 * socket插件
 */
export default {
  install(vue){

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
      nowTime() {
        return new Date().getTime();
      },
      // 打开socket连接
      open(heartbeatTimeout) {
        this.ws.onopen = ()=>{
          let heartbeatSendInterval = heartbeatTimeout / 2;
          this.lastSubscribeTime = this.nowTime();
          this.pingIntervalId = setInterval(()=>{
            let iv = this.nowTime() - this.lastSubscribeTime;
            // 超过一定时间自动与后台ping、pong  单位：秒
            if ((heartbeatSendInterval + iv) >= heartbeatTimeout) {
              this.send('ping');
            }
          }, heartbeatSendInterval);
        };
      },

      // 创建自定义事件
      createEvent(event, detail){
        let evt = document.createEvent('CustomEvent');
        evt.initCustomEvent(event, false, false, detail);
        return evt;
      },

      // 打开socket连接
      connect(heartbeatTimeout = 10000) {
        this.open(heartbeatTimeout);
        this.message();
        this.close();
      },

      // 订阅消息 ch 为订阅的频道 id 为订阅唯一标识
      subscribe(ch, id, token) {
        if(this.ws.readyState == 1) {
          let obj = {};
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
      on(id, callback) {
        let handler = function(e) {
          if(callback) callback(e.data, e);
        };
        this.tower.addEventListener(id, handler);
        let key = id + '_' + this.nowTime();
        this.events.push({key : key, handler: handler});
      },

      // 取消订阅
      unsubscribe(ch, id, token) {
        if(this.ws.readyState == 1) {
          let obj = {};
          if(ch) {
            obj.cancel = ch;
            obj.id = id;
            if(token) obj.authorization = token;
            if(this.channels.hasOwnProperty(id)) {
              delete this.channels[id];
              delete this.reactions[ch];
            }
            this.events = this.events.filter((v)=>{
              if(v.key.indexOf(id) !== -1) {
                // 失效ID解除监听
                this.tower.removeEventListener(id, v.handler);
              }else{
                return v;
              }
            });
            this.send(obj);
          }
        }
      },

      send(data){
        if(typeof data === 'object') {
          data = JSON.stringify(data);
        }
        this.ws.send(data);
      },

      message() {
        this.ws.onmessage = (evt)=>{
          let data = evt.data;
          this.lastSubscribeTime = this.nowTime();
          if(data) {
            if(data !== 'pong') data = JSON.parse(data);
            if(typeof data === 'object' && data.hasOwnProperty('ch')) {
              let e = this.reactions[data.ch];
              if(e) {
                e.data = data;
                this.tower.dispatchEvent(e);
              }
            }else if(data.hasOwnProperty('event')){
              let e = this.reactions[data.event];
              if(e) {
                e.data = data;
                this.tower.dispatchEvent(e);
              }
            }
          }
        }
      },

      close(callback) {
        this.ws.onclose = (evt)=>{
          clearInterval(this.pingIntervalId);
          if(callback) callback(evt, this.ws);
        }
      }
    };
    let ws = new SocketHandler(process.env.SOCKET_URL);
    // 将socket实例赋值到Vue原型链上
    vue.prototype.$socket = ws;
    // 初始化连接
    ws.connect()
  }
}
