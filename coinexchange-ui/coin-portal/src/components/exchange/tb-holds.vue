<template>
    <div class="trading-table-scroll">
      <table class="trading-table">
        <thead>
          <tr>
            <th>{{$t('m.exchange.orderID')}}</th>
            <th>{{$t('m.exchange.market')}}</th>
            <th>{{$t('m.exchange.positionDirection')}}</th>
            <th>{{$t('m.exchange.amount')}}</th>
            <th>{{$t('m.exchange.openPrice')}}</th>
            <th>{{$t('m.exchange.occupancyMargin')}}</th>
            <th>{{$t('m.exchange.profit')}}</th>
            <th>{{$t('m.exchange.fee')}}</th>
            <th>{{$t('m.exchange.currentPrice')}}</th>
            <th>{{$t('m.exchange.openTime')}}</th>
            <th>{{$t('m.exchange.operate')}}</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item,index) in positionDetails">
            <td v-text="item.orderId"></td>
            <td v-text="item.marketName"></td>
            <td>
              {{formatType(item)}}
            </td>
            <td v-text="item.num"></td>
            <td v-text="item.price"></td>
            <td v-text="item.lockMargin"></td>
            <td v-text="item.opsitionGainAndLoss"></td>
            <td v-text="item.fee"></td>
            <td v-text="item.currentPrice"></td>
            <td v-text="item.created"></td>
            <td>
              <el-button size="mini" @click="handleClosePostion(item)">{{$t('m.exchange.closePosition')}}</el-button>
            </td>
          </tr>
        </tbody>
      </table>
      <p class="no-data" v-if="positionDetails.length===0">{{$t('m.nodata')}}</p>
      <!--<p class="no-data" v-if="entrustTbData.length==0">暂无记录</p>-->

      <!--<el-pagination v-else-->

        <!--class="trade-page"-->
        <!--background-->
        <!--@current-change="entrustPageChange"-->
        <!--:page-size="7" layout=" prev, pager, next"-->
        <!--:total="entrustPageQuery.totalRecords">-->
      <!--</el-pagination>-->
    </div>
</template>
<script>
  import {exchangeUtil} from 'common/js/exchangeUtil'
	export default {
	  props:['positionDetails'],
    data(){
      return {
      }
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
        let type = 1
        if(row.type === 1){
          type = 2
        }else{
          type = 1
        }

        let dialogData = {
          type: type ,
          market:row.marketName,
          marketId:row.marketId,
          visible:true,
          orderId:row.id,
          num:row.num,
          symbol:row.marketName.replace("/","")
        }
        this.$emit('showOrderDialog', dialogData);
      }
    },
    watch:{
      height (nv){
        console.log(nv)
      }
    }
  }

</script>
