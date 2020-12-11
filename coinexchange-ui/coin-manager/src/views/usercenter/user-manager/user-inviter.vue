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
        prop="username"
        label="用户名"
        width="200">
      </el-table-column>

      <el-table-column
        prop="realName"
        label="真实姓名"
        width="200">
      </el-table-column>

      <el-table-column
        prop="created"
        label="创建时间"
        width="200">
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
  </div>
</template>

<script>
  import {userApi} from '@/api/userApi'
  import commonMixin from '@/components/mixin/common-mixin'

  export default {
    mixins: [commonMixin],
    props: [
      'userId',
    ],
    methods: {
      async listCallback() {
        const userId = this.userId;
        const data = await userApi.getDirectInviteidList(
          {userId},
          this.listQuery.current,
          this.listQuery.size
        );
        return data;
      },
    }
  }
</script>
