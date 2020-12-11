<template>
  <div>
    <img v-if="line&&line.url" :src="line.url" width="137" height="40">
    <!--<canvas ref="canvas" class="canvas" width="137" height="40"></canvas>-->
  </div>
</template>
<script>
export default {
  data () {
    return {
      line: {
        panel_w: 137,
        panel_h: 40,
        padding_t: 10,
        padding_l: 10,
        url: ''
      }
    }
  },
  props: {
    trendData: Array
  },
  // mounted () {
  //   this._drawLine();
  // },
  watch: {
    trendData: {
      handler () {
        this._drawLine();
      },
      deep: true
    }
  },
  methods: {
    draw (data) {
      let { panel_w, panel_h, padding_t, padding_l } = this.line;    //画板宽/高
      let count = 4,    //水平分段数
        dis = (panel_w - padding_t * 2) / (count - 1),    //每段距离
        spe = panel_h - padding_t * 2;    //画板可绘画区域高
      let color = '#409EFF';

      if (!data) return;

      count = data.length;
      dis = (panel_w - padding_t * 2) / (count - 1);
      let canvas = document.createElement('canvas');
      let context = canvas.getContext("2d");
      canvas.width = panel_w;
      canvas.height = panel_h;
      // context.clearRect(0,0,panel_w,panel_h);

//      context.fillStyle="white";
//      context.fillRect(0,0,panel_w,panel_h);

      let max = 0, min = 10000;    //获取最高／低点
      let rate;    //画板可绘画区域高 与 高低差值 比率
      let points = [];
      for (let i = 0; i < data.length; i++) {
        max = Math.max(max, data[i]);
        min = Math.min(min, data[i]);
      }
      rate = spe / (max - min) || 1;
      for (let i = 0; i < data.length; i++) {
        let x = i * dis + padding_l;
        let y = (max - data[i]) * rate + padding_t || panel_h / 2;
        points.push([x, y]);
      }
      context.lineWidth = 1;
      context.lineWidth = 1;
      context.strokeStyle = color;
      for (let i = 0; i < points.length; i++) {
        if (i == 0) { context.moveTo(points[i][0], points[i][1]) } else { context.lineTo(points[i][0], points[i][1]) }
      }
      context.stroke();
      this.line.url = canvas.toDataURL('image/png');
    },
    _drawLine () {
      if (this.trendData) {
        if (this.trendData.length == 0) this.draw([1, 1]);
        if (this.trendData.length == 1) this.draw([1, 1]);
        if (this.trendData.length > 1) this.draw(this.trendData);
      } else {
        this.draw([1, 1]);
      }
    }
  },
  created () {
    this._drawLine();
  }
}
</script>
<style scoped lang="stylus" rel="stylesheet/stylus">
  .canvas{
    display: inline-block;
    vertical-align: baseline;
  }

</style>
