package com.cloud.shangwu.businesscloud.mvp.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

import java.io.Serializable

class ChooseHobbiesBentest : Serializable, MultiItemEntity {

    /**
     * code : 200
     * data : [{"hid":1,"higher":0,"content":"运动健身","label":null,"status":null,"createTime":null,"children":[{"hid":2,"higher":1,"content":"跑步","label":null,"status":null,"createTime":null,"children":[]},{"hid":3,"higher":1,"content":"游泳","label":null,"status":null,"createTime":null,"children":[]},{"hid":4,"higher":1,"content":"射击","label":null,"status":null,"createTime":null,"children":[]}]}]
     * message : 操作成功
     */


    var data: List<DataBean>? = null

    override fun getItemType(): Int {
        return 0
    }

    class DataBean : MultiItemEntity {
        /**
         * hid : 1
         * higher : 0
         * content : 运动健身
         * label : null
         * status : null
         * createTime : null
         * children : [{"hid":2,"higher":1,"content":"跑步","label":null,"status":null,"createTime":null,"children":[]},{"hid":3,"higher":1,"content":"游泳","label":null,"status":null,"createTime":null,"children":[]},{"hid":4,"higher":1,"content":"射击","label":null,"status":null,"createTime":null,"children":[]}]
         */

        var hid: Int = 0
        var higher: Int = 0
        var content: String? = null
        var label: Any? = null
        var status: Any? = null
        var createTime: Any? = null
        var children: List<ChildrenBean>? = null

        override fun getItemType(): Int {
            return higher
        }

        class ChildrenBean : MultiItemEntity {
            /**
             * hid : 2
             * higher : 1
             * content : 跑步
             * label : null
             * status : null
             * createTime : null
             * children : []
             */

            var hid: Int = 0
            var higher: Int = 0
            var content: String? = null
            var label: Any? = null
            var status: Any? = null
            var createTime: Any? = null
            var children: List<*>? = null

            override fun getItemType(): Int {
                return higher
            }
        }
    }
}
