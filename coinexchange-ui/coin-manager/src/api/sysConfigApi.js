import request from '@/utils/request'
import {formUtils} from '@/utils/formUtil'

export const sysConfigApi = {

  /**
   * 获取功能权限列表
   * @param current
   * @param size
   */
  getPowerList(current, size) {
    const params = {}
    params.current = current
    params.size = size
    return request({
      url: '/admin/privileges',
      method: 'get',
      params: params
    })
  },

  getUsers(form, current, size) {
    const params = {}
    for (const item in form) {
      if (form[item]) {
        params[item] = form[item]
      }
    }
    params.current = current
    params.size = size
    return request({
      url: '/admin/users',
      method: 'get',
      params: params
    })
  },

  /**
   * 新建员工
   */
  createEmployee(data) {
    console.log('员工状态：' + data.status)

    return request({
      url: '/admin/users',
      method: 'post',
      data: data
    })
  },
  /**
   * 更新员工
   */
  updateEmployee(data) {
    return request({
      url: '/admin/users',
      method: 'patch',
      data: data
    })
  },
  /**
   * 删除员工
   */
  deleteEmployee(ids) {
    return request({
      url: '/admin/users/delete',
      method: 'post',
      data: ids
    })
  },

  // 角色相关

  /**
   * 获取角色权限列表
   * @param current
   * @param size
   */
  getRoleList(form, current, size) {
    const params = {}
    for (const item in form) {
      if (form[item]) {
        params[item] = form[item]
      }
    }
    params.current = current
    params.size = size
    return request({
      url: '/admin/roles',
      method: 'get',
      params: params
    })
  },

  /**
   * 新建角色
   */
  createRole(data) {
    return request({
      url: '/admin/roles',
      method: 'post',
      data: data
    })
  },

  /**
   * 删除角色
   */
  deleteRole(ids) {
    return request({
      url: '/admin/roles/delete',
      method: 'post',
      data: ids
    })
  },

  /**
   * 新增员工
   */
  updateRole(data) {
    return request({
      url: '/admin/users/update',
      method: 'put',
      data: data
    })
  },

  /**
   * 获取所有权限列
   * @param id
   */
  getRolePrivileges(id) {
    return request({
      url: '/admin/roles_privileges',
      method: 'get',
      params: {
        roleId: id
      }
    })
  },

  /**
   * 配置权限
   * @param id
   * @param privilegeIds
   */
  postRolePrivileges(id, privilegeIds) {
    return request({
      url: '/admin/grant_privileges',
      method: 'post',
      data: {
        roleId: id,
        privilegeIds
      }
    })
  },

  /**
   * 获取所有系统日志
   * @param id
   */
  getSysUserLog(form, current, size) {
    let params = {}
    formUtils.formDateRange(params, form);
    params.current = current
    params.size = size
    return request({
      url: '/admin/sysUserLog',
      method: 'get',
      params: params
    })
  }
}

