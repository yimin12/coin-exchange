<template>
  <div class="trading-table-scroll">
    <table class="trading-table">
      <thead>
        <tr>
          <th>{{$t('m.exchange.orderID')}}</th>
          <th>{{$t('m.exchange.market')}}</th>
          <th>{{$t('m.exchange.transType')}}</th>
          <th>{{$t('m.exchange.amount')}}</th>
          <th>{{$t('m.exchange.donePrice')}}</th>
          <th>{{$t('m.exchange.fee')}}</th>
          <th>{{$t('m.exchange.doneTime')}}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item,index) in turnoverOrders">
          <td v-text="item.id"></td>
          <td v-text="item.marketName"></td>
          <td v-text="formatEntrustType(item)"></td>
          <td v-text="item.volume"></td>
          <td v-text="item.price"></td>
          <td v-text="item.fee"></td>
          <td v-text="item.created"></td>
        </tr>
      </tbody>
    </table>
    <p class="no-data" v-if="turnoverOrders.length===0">{{$t('m.nodata')}}</p>
  </div>
<!--     <div class="position-detail">
  <el-table
    :data="turnoverOrders"
    :height="200"
    row-class-name="table-row-class"
    header-cell-class-name="table-header-class"
    :default-sort="{prop: 'date', order: 'descending'}"
  >
    <el-table-column
      prop="id"
      label="成交单号"
      align="center"
    >
    </el-table-column>
    <el-table-column
      prop="name"
      label="品种"
      align="center"
    >
    </el-table-column>
    <el-table-column
      label="成交类型"
      align="center"
      :formatter="formatEntrustType"
    >
    </el-table-column>
    <el-table-column
      prop="turnoverNum"
      label="数量"
      align="center"

    >
    </el-table-column>

    <el-table-column
      prop="turnoverPrice"
      label="成交价"
      align="center"
    >
    </el-table-column>


    <el-table-column
      prop="fee"
      label="手续费"
      align="center"
      :formatter="formatFee"
    >
    </el-table-column>

    <el-table-column
      prop="createTime"
      label="成交时间"
      align="center"
    >
    </el-table-column>

  </el-table>
</div> -->
</template>
<script>
  import {exchangeUtil} from 'common/js/exchangeUtil'
	export default {
	  props:['turnoverOrders'],
    data(){
      return {
      }
    },
    mounted(){

    },
    methods:{
      formatFee(row){
        if(row.type === 1){
          return row.dealBuyFee
        }else{
          return row.dealSellFee
        }
      },
      formatNumber(num){
        return num
      },
      formatEntrustType(row, column){
       return exchangeUtil.formatEntrustType(row)
      },
      handleClosePostion(){
        this.$emit('showOrderDialog' , {type:0,price:11});
      }
    }
  }

</script>
