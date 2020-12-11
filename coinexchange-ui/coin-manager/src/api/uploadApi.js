import request from '@/utils/request'

export const uploadApi = {
  // 此接口不带admin前缀
  getPreUpload() {
    return request({
      url: '/admin/image/pre/upload',
      method: 'GET'
    })
  }
}
