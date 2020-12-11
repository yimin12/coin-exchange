<template>
    <div class="trading-table-scroll">
      <table class="trading-table">
        <thead>
          <tr>
            <th>{{$t('m.exchange.openOrderId')}}</th>
            <th>{{$t('m.exchange.closeOrderId')}}</th>
            <th>{{$t('m.exchange.market')}}</th>
            <th>{{$t('m.exchange.direction')}}</th>
            <th>{{$t('m.exchange.closeAmount')}}</th>
            <th>{{$t('m.exchange.openPrice')}}</th>
            <th>{{$t('m.exchange.closePrice')}}</th>
            <th>{{$t('m.exchange.closeProfit')}}</th>
            <th>{{$t('m.exchange.fee')}}</th>
            <th>{{$t('m.exchange.openTime')}}</th>
            <th>{{$t('m.exchange.closeTime')}}</th>
            <th>{{$t('m.exchange.unlockMargin')}}</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item,index) in todayCloseoutData">
            <td v-text="item.openOrderId"></td>
            <td v-text="item.closeOrderId"></td>
            <td v-text="item.marketName"></td>
            <td v-text="formatType(item)"></td>
            <td v-text="item.volume"></td>
            <td v-text="item.openPrice"></td>
            <td v-text="item.closePrice"></td>
            <td v-text="item.profit"></td>
            <td v-text="item.fee"></td>
            <td v-text="item.openTime"></td>
            <td v-text="item.closeTime"></td>
            <td v-text="item.unlockMargin"></td>
          </tr>
        </tbody>
      </table>
      <p class="no-data" v-if="todayCloseoutData.length===0">{{$t('m.nodata')}}</p>
    </div>
</template>
<script>
  import {exchangeUtil} from 'common/js/exchangeUtil'
	export default {
	  props:['todayCloseoutData'],
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
      formatNumber(num){
        return num
      },
      formatEntrustType(row, column){
        if(row.priceType == 1){
          if(row.type == 1){
            return this.$t('m.exchange.marketBuy')
          }else{
            return this.$t('m.exchange.marketSell')
          }
        }else{
          if(row.type == 1){
            return this.$t('m.exchange.fixedBuy')
          }else{
            return this.$t('m.exchange.fixedSell')
          }
        }
      },
      handleClosePostion(row){


        console.log("")

      }
    }
  }

</script>
