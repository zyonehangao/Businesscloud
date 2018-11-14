package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import com.contrarywind.interfaces.IPickerViewData

class JsonData {


    var code: Int = 0
    var message: String? = null
    var data: List<DataBean>? = null

    class DataBean : IPickerViewData {
        override fun getPickerViewText(): String {
            return this.content
        }


        var did: Int = 0
        var higher: Int = 0
        var content: String = ""
        var label: Any? = null
        var status: Any? = null
        var createTime: Any? = null
        var children: ArrayList<ChildrenBeanX>?= null

        class ChildrenBeanX {


            var did: Int = 0
            var higher: Int = 0
            var content: String? = null
            var label: Any? = null
            var status: Any? = null
            var createTime: Any? = null
            var children: ArrayList<ChildrenBean>? = null

            class ChildrenBean {
                /**
                 * did : 130102
                 * higher : 130100
                 * content : 长安区
                 * label : null
                 * status : null
                 * createTime : null
                 * children : null
                 */

                var did: Int = 0
                var higher: Int = 0
                var content: String = ""
                var label: Any? = null
                var status: Any? = null
                var createTime: Any? = null
                var children: Any? = null
            }
        }
    }
}
