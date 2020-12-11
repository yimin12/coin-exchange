<template>
  <div class="config-main">
    <el-checkbox :indeterminate="isIndeterminate" :key="" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
    <el-form :model="ruleForm" ref="ruleForm">
      <el-form-item class="par-name" :key="par.id" :label="par.name" v-for="par in configRule" v-if="par.privileges.length>0">
        <el-checkbox @change="handleCheckedChange(child)" v-model="child.checked" v-for="child in par.privileges" :key="child.id" :label="child.description"></el-checkbox>
      </el-form-item>
      <el-row type="flex" class="operation-container" justify="flex-start">
        <el-col :span="3">
          <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="danger" @click="handleCancel">返回</el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
  import {sysConfigApi} from "../../../api/sysConfigApi";

  export default {
    data() {
      return {
        checkAll         : false,
        id               : '',
        ruleForm         : {},
        configRule       : [],
        checkList        : [],
        isIndeterminate  : false,
        privilegeIds     : [],
        countPrivilegeIds: []
      };
    },
    async mounted() {
      let that = this;
      let id = this.$route.params.id;
      this.id = id;
      await sysConfigApi.getRolePrivileges(id).then(function (data) {
        that.boolPrivileges(data);
        that.configRule = data;

      });
      // console.log(that.countPrivilegeIds, that.privilegeIds);
    },
    methods: {
      submitForm(formName) {
        let id = this.$route.params.id;
        let that = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // alert('submit!');

            sysConfigApi.postRolePrivileges(id, that.privilegeIds);
            that.$notify({
              title   : '成功',
              message : '配置权限成功',
              type    : 'success',
              duration: 2000
            });
            that.$router.push("../role-manager");
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },

      handleCheckAllChange() {
        // this.checkedCities = val ? cityOptions : [];
        this.isIndeterminate = false;
        this.boolPrivileges(this.configRule, this.checkAll);
        // console.log(this.configRule);
      },

      handleCancel() {
        this.$router.push("../role-manager");
      },

      //判断状态
      statusCheck() {
        if (this.privilegeIds.length == 0) {
          this.isIndeterminate = false;
        }

        if (this.privilegeIds.length > 0) {
          this.isIndeterminate = true;
          if (this.privilegeIds.length == this.countPrivilegeIds.length) {
            this.checkAll = true;
            this.isIndeterminate = false;
          } else {
            this.checkAll = false;
            this.isIndeterminate = true;
          }
        }
      },

      handleCheckedChange(item) {
        // this.boolPrivileges(this.configRule)
        if (item.checked) {
          this.privilegeIds.push(item.id);
        } else {
          for (let i = 0; i < this.privilegeIds.length; i ++) {
            if (this.privilegeIds[i] == item.id) {
              this.privilegeIds.splice(i, 1);
            }
          }
        }

        this.statusCheck();

        // console.log(this.privilegeIds, this.countPrivilegeIds);
      },

      /**
       * data传入的数据,bool全选值
       * @param data
       * @param bool
       */
      boolPrivileges(data, bool) {
        for (let i in data) {
          if (typeof(bool) == "undefined") {
            if (typeof(data[i].own) != "undefined") {
              this.countPrivilegeIds.push(data[i].id);
              if (data[i].own === 1) {
                data[i].checked = true;
                this.privilegeIds.push(data[i].id);
              } else {
                data[i].checked = false;
              }
              this.statusCheck();

            }
          } else if (bool) {
            if (typeof(data[i].own) != "undefined") {
              data[i].checked = true;
              this.privilegeIds.push(data[i].id);
            }
          } else if (! bool) {
            data[i].checked = false;
            this.privilegeIds = [];
          }
          this.boolPrivileges(data[i].privileges, bool);
        }
      }
    }
  };
</script>

<style scoped>


</style>
