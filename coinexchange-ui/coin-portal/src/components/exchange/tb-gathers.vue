<template>
    <div class="trading-table-scroll">
      <table class="trading-table">
        <thead>
          <tr>
            <th>{{$t('m.exchange.closeTime')}}</th>
            <th>{{$t('m.exchange.market')}}</th>
            <th>{{$t('m.exchange.positionDirection')}}</th>
            <th>{{$t('m.exchange.amount')}}</th>
            <th>{{$t('m.exchange.averagePrice')}}</th>
            <th>{{$t('m.exchange.profit')}}</th>
            <th>{{$t('m.exchange.fee')}}</th>
            <th>{{$t('m.exchange.currentPrice')}}</th>
            <th>{{$t('m.exchange.operate')}}</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item,index) in positionSummary">
            <td v-text="item.openTime"></td>
            <td v-text="item.marketName"></td>
            <td v-text="formatType(item)"></td>
            <td v-text="item.volume"></td>
            <td v-text="item.avgPrice"></td>
            <td v-text="item.profit"></td>
            <td v-text="item.fee"></td>
            <td v-text="item.currentPrice"></td>
            <td>
              <el-button size="mini" @click="handleClosePostion(item)">{{$t('m.exchange.closePosition')}}</el-button>
            </td>
          </tr>
        </tbody>
      </table>
      <p class="no-data" v-if="positionSummary.length===0">{{$t('m.nodata')}}</p>
    </div>
</template>
<script>
  import {exchangeUtil} from 'common/js/exchangeUtil'

  export default {
    props:['positionSummary'],
	  data(){
      return {
//        columnWidth        : [240, 240, 240, 240, 240],
      }
    },
    mounted(){

    },
    methods:{
      formatType(row){
        if(row.type === 1){
          return this.$t('m.exchange.buying')
        }else{
          return this.$t('m.exchange.selling')
        }
      },
      formatEntrustType(row){
        return exchangeUtil.formatEntrustType(row)
      },
      formatNumber(num){
        return num
      },
      handleClosePostion(row){
        let type = 1
        if(row.type === 1){
          type = 2
        }else{
          type = 1
        }

        let dialogData = {
          type: type,
          market:row.marketName,
          marketId:row.marketId,
          visible:true,
          num:row.volume,
          symbol:row.marketName.replace("/",""),
        }
        this.$emit('showOrderDialog', dialogData);
      }
    }
  }

</script>
