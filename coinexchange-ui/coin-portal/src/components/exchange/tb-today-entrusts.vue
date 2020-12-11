<template>
  <div class="trading-table-scroll">
    <table class="trading-table">
      <thead>
        <tr>
          <th>{{$t('m.exchange.orderID')}}</th>
          <th>{{$t('m.exchange.market')}}</th>
          <th>{{$t('m.exchange.entrustType')}}</th>
          <th>{{$t('m.exchange.amount')}}/{{$t('m.exchange.deal')}}</th>
          <th>{{$t('m.exchange.entrustPrice')}}/{{$t('m.exchange.averageTransPrice')}}</th>
          <th>{{$t('m.exchange.fee')}}</th>
          <th>{{$t('m.exchange.priceRange')}}</th>
          <th>{{$t('m.exchange.entrustTime')}}</th>
          <th>{{$t('m.exchange.operate')}}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item,index) in entrustOrders">
          <td v-text="item.orderId"></td>
          <td v-text="item.marketName"></td>
          <td v-text="formatEntrustType(item)"></td>
          <td >{{item.volume}} / {{item.dealVolume}}</td>
          <td v-text="formatNumber(item)"></td>
          <td v-text="item.fee"></td>
          <td v-text="item.priceRange"></td>
          <td v-text="item.created"></td>
          <td>
            <el-button size="mini" @click="handleCancelOrder(item)" v-if="item.status==0">{{$t('m.exchange.cancel')}}</el-button>
            <span v-else-if="item.status == 1">{{$t('m.exchange.deal')}}</span>
            <span v-else-if="item.status == 2">{{$t('m.exchange.canceled')}}</span>
            <span v-else>{{$t('m.exchange.abnormal')}}</span>

          </td>
        </tr>
      </tbody>
    </table>
    <p class="no-data" v-if="entrustOrders.length===0">{{$t('m.nodata')}}</p>
  </div>
</template>
<script>
  import {exchangeUtil} from 'common/js/exchangeUtil'
  import {exchangeApi} from 'api/exchange'
  import {mapGetters} from 'vuex'

  export default {
    props: ['entrustOrders'],

    data() {
      return {
//        columnWidth        : [240, 240, 240, 240, 240],
      }
    },
    mounted() {

    },
    computed:{
      ...mapGetters(['token'])
    },
    methods: {
      formatEntrustType(row) {
        return exchangeUtil.formatEntrustType(row)
      },
      formatNumber(item){

        if(item.dealAvgPrice == 0){
          return item.price +"/ --"

        }else{
          return item.price +"/" +item.dealAvgPrice

        }
      },

      handleCancelOrder(row) {
        this.$confirm(this.$t('m.exchange.whetherCancelOrder'), this.$t('m.prompt'), {
          confirmButtonText: this.$t('m.yes'),
          cancelButtonText : this.$t('m.no'),
          type             : 'warning'
        }).then(() => {
          exchangeApi.entrustCancel(row.orderId, this.token).then(res => {
            this.$message({
              type   : 'success',
              message: this.$t('m.exchange.cancelOrderCallback')
            });
          })
        });
      }
    }
  }

</script>
