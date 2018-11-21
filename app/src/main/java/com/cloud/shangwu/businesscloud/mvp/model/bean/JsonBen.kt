package com.cloud.shangwu.businesscloud.mvp.model.bean

import com.contrarywind.interfaces.IPickerViewData

class JsonData {


    private var code: Int = 0
    var data: List<JsonData.DataBean>? = null
    private var message: String? = null
    fun setCode(code: Int) {
        this.code = code
    }

    fun getCode(): Int {
        return code
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getMessage(): String? {
        return message
    }


    open class DataBean : IPickerViewData {
        override fun getPickerViewText(): String {
            return this.content
        }

        var did: String? = null
        var higher: String? = null
        var content: String = ""
        var label: String? = null
        var status: String? = null
        var createTime: String? = null
        var children: List<JsonData.DataBean.ChildrenBeanX>? = null

        open class ChildrenBeanX : IPickerViewData {
            override fun getPickerViewText(): String {
                return this.content
            }

            var did: String? = null
            var higher: String? = null
            var content: String = ""
            var label: String? = null
            var status: String? = null
            var createTime: String? = null
            var children: List<ChildrenBean>? = null

            open class ChildrenBean : IPickerViewData {
                override fun getPickerViewText(): String {
                    return this.content
                }

                var did: String? = null
                var higher: String? = null
                var content: String = ""
                var label: String? = null
                var status: String? = null
                var createTime: String? = null
                var children: List<ChildrenBean>? = null

            }


        }


    }


}
