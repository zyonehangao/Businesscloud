package com.cloud.shangwu.businesscloud.mvp.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

class ChooseHobbiseData {
    /**
     * code : 200
     * data : [{"hid":5,"higher":0,"content":"休闲类","label":null,"status":null,"createTime":null,"children":[{"hid":1,"higher":5,"content":"运动健身","label":null,"status":null,"createTime":null,"children":[{"hid":2,"higher":1,"content":"跑步","label":null,"status":null,"createTime":null,"children":null},{"hid":3,"higher":1,"content":"游泳","label":null,"status":null,"createTime":null,"children":null},{"hid":4,"higher":1,"content":"射击","label":null,"status":null,"createTime":null,"children":null}]}]}]
     * message : 操作成功
     */

    private var code: Int = 0
    private var message: String? = null
    private var data: List<DataBean>? = null

    fun getCode(): Int {
        return code
    }

    fun setCode(code: Int) {
        this.code = code
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getData(): List<DataBean>? {
        return data
    }

    fun setData(data: List<DataBean>) {
        this.data = data
    }

    class DataBean {
//        override fun getItemType(): Int =itemType

        /**
         * hid : 5
         * higher : 0
         * content : 休闲类
         * label : null
         * status : null
         * createTime : null
         * children : [{"hid":1,"higher":5,"content":"运动健身","label":null,"status":null,"createTime":null,"children":[{"hid":2,"higher":1,"content":"跑步","label":null,"status":null,"createTime":null,"children":null},{"hid":3,"higher":1,"content":"游泳","label":null,"status":null,"createTime":null,"children":null},{"hid":4,"higher":1,"content":"射击","label":null,"status":null,"createTime":null,"children":null}]}]
         */

        var hid: Int = 0
        var higher: Int = 0
        var content: String? = null
        var label: Any? = null
        var status: Any? = null
        var createTime: Any? = null
        var children: List<ChildrenBeanX>? = null

        class ChildrenBeanX : MultiItemEntity {
            private var itemType: Int = 0
            override fun getItemType(): Int =itemType
            constructor(itemType: Int, hid: Int, higher: Int, content: String?, label: Any?, children: List<ChildrenBean>?) {
                this.itemType = itemType
                this.hid = hid
                this.higher = higher
                this.content = content
                this.label = label
                this.children=children
            }
            /**
             * hid : 1
             * higher : 5
             * content : 运动健身
             * label : null
             * status : null
             * createTime : null
             * children : [{"hid":2,"higher":1,"content":"跑步","label":null,"status":null,"createTime":null,"children":null},{"hid":3,"higher":1,"content":"游泳","label":null,"status":null,"createTime":null,"children":null},{"hid":4,"higher":1,"content":"射击","label":null,"status":null,"createTime":null,"children":null}]
             */
            companion object {
                var Heard :Int= 0
                var Text :Int= 1
            }

            var hid: Int = 0
            var higher: Int = 0
            var content: String? = null
            var label: Any? = null
            var status: Any? = null
            var createTime: Any? = null
            var children: List<ChildrenBean>? = null

            class ChildrenBean {

                /**
                 * hid : 2
                 * higher : 1
                 * content : 跑步
                 * label : null
                 * status : null
                 * createTime : null
                 * children : null
                 */

                var hid: Int = 0
                var higher: Int = 0
                var content: String? = null
                var label: Any? = null
                var status: Any? = null
                var createTime: Any? = null
                var children: Any? = null
                var select: Boolean = false
            }
        }
    }
//    /**
//     * code : 200
//     * data : [{"hid":1,"higher":0,"content":"运动健身","label":null,"status":null,"createTime":null,"children":[{"hid":2,"higher":1,"content":"跑步","label":null,"status":null,"createTime":null,"children":[]},{"hid":3,"higher":1,"content":"游泳","label":null,"status":null,"createTime":null,"children":[]},{"hid":4,"higher":1,"content":"射击","label":null,"status":null,"createTime":null,"children":[]}]}]
//     * message : 操作成功
//     */
//
//    var code: Int = 0
//    var message: String? = null
//
//    var data: List<DataBean>? = null
//
//    class DataBean {
//        /**
//         * hid : 1
//         * higher : 0
//         * content : 运动健身
//         * label : null
//         * status : null
//         * createTime : null
//         * children : [{"hid":2,"higher":1,"content":"跑步","label":null,"status":null,"createTime":null,"children":[]},{"hid":3,"higher":1,"content":"游泳","label":null,"status":null,"createTime":null,"children":[]},{"hid":4,"higher":1,"content":"射击","label":null,"status":null,"createTime":null,"children":[]}]
//         */
//
//        var hid: Int = 0
//        var higher: Int = 0
//        var content: String? = null
//        var label: Any? = null
//        var status: Any? = null
//        var createTime: Any? = null
//        var children: List<ChildrenBean>? = null
//
//        class ChildrenBean : MultiItemEntity {
//            private var itemType: Int = 0
//
//            /**
//             * hid : 2
//             * higher : 1
//             * content : 跑步
//             * label : null
//             * status : null
//             * createTime : null
//             * children : []
//             */
//
//            var hid: Int = 0
//            var higher: Int = 0
//            var content: String? = null
//            var label: Any? = null
//            var status: Any? = null
//            var createTime: Any? = null
//            companion object {
//                var Heard = 0
//                var Text = 1
//            }
//            private var children: List<String>? = null
//
//            constructor(itemType:Int,hid: Int, higher: Int, content: String?, label: Any?) {
//                this.itemType = itemType
//                this.hid = hid
//                this.higher = higher
//                this.content = content
//                this.label = label
//            }
//
//            constructor()
//
//            fun getChildren(): List<*>? {
//                return children
//            }
//
//            fun setChildren(children: List<String>) {
//                this.children = children
//            }
//
//            override fun getItemType(): Int {
//                return itemType
//            }
//
//
//        }
//    }
}
