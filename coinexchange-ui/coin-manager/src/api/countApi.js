import request from '@/utils/request'

export const countApi = {

    /**
     * 获取统计列表
     */
    getCountList(form, current, size, url) {
        let params = {};
        for (let itme in form) {

            if (form[itme]) {
                let startTime = form.dateRange[0];
                let endTime = form.dateRange[1];
                params["startTime"] = startTime;
                params["endTime"] = endTime;
            } else {
                params[itme] = form[itme];
            }
        }
        params["current"] = current;
        params["size"] = size;
        console.log("参数-结果:", params);
        return request({
            url: `${url}`,
            method: 'get',
            params: params
        })
    }
}