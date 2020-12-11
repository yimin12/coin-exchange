<template>
  <div>
    <el-table
      ref="multipleTable"
      :data="listData"
      tooltip-effect="dark"
      style="width: 100%"
      v-loading="listLoading">

      <el-table-column
        prop="id"
        label="ID"
        width="180">
      </el-table-column>

      <el-table-column
        prop="remark"
        label="银行卡名称"
        width="200">
      </el-table-column>

      <el-table-column
        prop="bank"
        label="开户行"
        width="150">
      </el-table-column>

      <el-table-column
        prop="realName"
        label="开户人"
        width="100">
      </el-table-column>

      <el-table-column
        prop="bankCard"
        label="开户账号"
        width="250">

      </el-table-column>

      <el-table-column
        prop="status"
        label="状态"
        width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status|elTagFilter">
            {{scope.row.status | statusFilter}}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column
        prop="created"
        label="创建时间"
        width="180">
      </el-table-column>

      <el-table-column
        label="操作"
        width="200"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="editBank(scope.$index, scope.row)" icon="el-icon-edit">编辑
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
      class="pagination-container"
      background
      layout="total,prev, pager, next"
      :current-page.sync="listQuery.current"
      :page-size="listQuery.size"
      :total="listQuery.total"
      @current-change="handlePageChange"
    >
    </el-pagination>
    <user-bank-dialog :userId="userId"
                      ref="userBankDialog"
                      @refreshList="_getList"></user-bank-dialog>
  </div>
</template>

<script>
  import {userApi} from '@/api/userApi'
  import commonMixin from '@/components/mixin/common-mixin'
  import userBankDialog from './user-bank-dialog'

  export default {
    mixins: [commonMixin],
    components: {userBankDialog},
    props: [
      'userId',
    ],
    methods: {
      listCallback() {
        const userId = this.userId;
        return userApi.getBankList(
          userId,
          this.listQuery.current,
          this.listQuery.size
        );
      },
      editBank(index, row) {
        this.$refs.userBankDialog.showDialog(2, {...row});
      },
      async changeStatusCallback(id, status) {
        return await userApi.updateBankStatus(id,status);
      },
    }
  }
</script>
