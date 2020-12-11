<template>
  <div class="common-main">

    <el-form :model="searchForm" ref="searchForm" label-width="100px" class="search-container">

      <el-form-item label="标题" prop="title">
        <el-input v-model="searchForm.title" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="币种名称" prop="name">
        <el-input v-model="searchForm.name" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="币种类型" prop="type">
        <el-select v-model="searchForm.type" class="form-input" clearable>
          <el-option v-for="(item,index) in coinTypeOptions"
                     :key="index"
                     :label="item.code"
                     :value="item.code"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="钱包类型" prop="wallet_type">
        <el-select v-model="searchForm.wallet_type" class="form-input" clearable>
          <el-option v-for="(value,key) in select.wallet_type"
                     :key="key"
                     :label="value"
                     :value="key"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select v-model="searchForm.status" class="form-input" clearable>
          <el-option v-for="(value,key) in select.status"
                     :key="key"
                     :label="value"
                     :value="key"></el-option>
        </el-select>
      </el-form-item>

      <div class="operation-container">
        <el-button icon="el-icon-search" @click="submitForm('searchForm')">搜索
        </el-button>
        <el-button type="primary" icon="el-icon-edit" @click="handleCreate">新建</el-button>
      </div>

    </el-form>

    <el-table
      class="test"
      ref="multipleTable"
      :data="listData"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange"
      v-loading="listLoading">
      <el-table-column
        prop="id"
        label="ID"
        width="100">
      </el-table-column>
      <el-table-column
        prop="name"
        label="币种名称"
      >
      </el-table-column>
      <el-table-column
        prop="title"
        label="标题"
      >
      </el-table-column>
      <el-table-column
        prop="img"
        label="币种Logo"
        width="100"
      >
        <template slot-scope="scope">
          <img class="logoImg" v-bind:src="scope.row.img"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="type"
        label="类型"
        width="100"

      >
      </el-table-column>
      <el-table-column
        prop="wallet"
        label="钱包类型"
        width="100"
      >
        <template slot-scope="scope">
          {{select.wallet_type[scope.row.wallet]}}
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        render="renderHeader"
        label="状态"
        width="100"
      >
        <template slot-scope="scope">
          <el-tag :type="scope.row.status|elTagFilter">
            {{select.status[scope.row.status]}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="rechargeFlag"
        render="renderHeader"
        label="充值状态"
        width="100"
      >
        <template slot-scope="scope">
          <el-tag :type="scope.row.rechargeFlag|elTagFilter">
            {{select.rechargeFlag[scope.row.rechargeFlag]}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="withdrawFlag"
        render="renderHeader"
        label="提币状态"
        width="100"
      >
        <template slot-scope="scope">
          <el-tag :type="scope.row.withdrawFlag|elTagFilter">
            {{select.withdrawFlag[scope.row.withdrawFlag]}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="200"
        align="center"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="handleEdit(scope.$index, scope.row)" icon="el-icon-edit">编辑
          </el-button>
          <el-button
            size="mini"
            v-show="scope.row.status===0"
            @click="handleChangeStatus(scope.$index, scope.row)" icon="el-icon-edit">启用
          </el-button>
          <el-button
            size="mini"
            type="danger"
            v-show="scope.row.status===1"
            @click="handleChangeStatus(scope.$index, scope.row)" icon="el-icon-edit">禁用
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagination-container dialog-footer"
      background
      layout="total,prev, pager, next"
      :current-page.sync="listQuery.current"
      :page-size="listQuery.size"
      :total="listQuery.total"
      @current-change="handlePageChange"
    >
    </el-pagination>
  </div>
</template>

<script>
  import {coinConfigApi} from '@/api/coinConfigApi'
  import commonMixin from '@/components/mixin/common-mixin'
  import {selectOption} from './common'
  import {mapGetters} from 'vuex'

  export default {
    mixins: [commonMixin],
    computed: {
      ...mapGetters(['getCoinTypeAll'])
    },
    data() {
      return {
        searchForm: {
          id: '',
          name: '',
          type: '',
          status: '',
          title: '',
          wallet_type: ''
        },
        select: selectOption,
        coinTypeOptions: null
      }
    },
    async mounted() {
      await this.$store.dispatch("CoinTypeAll")
      this.coinTypeOptions = this.getCoinTypeAll
    },

    methods: {
      listCallback() {
        this.$store.dispatch("CoinAll")
        return coinConfigApi.getCoinList(this.searchForm,
          this.listQuery.current, this.listQuery.size)
      },
      async changeStatusCallback(id, status) {
        return await coinConfigApi.setStatus({
          id,
          status,
        });
      },
      handleCreate() {
        this.$router.push({path: `/trade-config/coin-config/coin-detail/add/0`})
      },
      handleEdit(index, row) {
        //认购币隐藏二三tab
        let hiddenTab = false
        if (row.wallet === 'rgb') {
          hiddenTab = true
        }
        this.$router.push({
          path: `/trade-config/coin-config/coin-detail/edit/${row.id}`,
          query: {hiddenTab: hiddenTab}
        })
      },
      handleDetail(index, row) {
        this.$router.push({path: `/trade-config/coin-config/coin-detail/detail/${row.id}`})
      },
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
