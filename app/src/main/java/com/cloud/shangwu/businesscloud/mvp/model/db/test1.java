package com.cloud.shangwu.businesscloud.mvp.model.db;

import java.util.List;

public class test1 {

    /**
     * code : 200
     * data : [{"hid":5,"higher":0,"content":"休闲类","label":null,"status":null,"createTime":null,"children":[{"hid":1,"higher":5,"content":"运动健身","label":null,"status":null,"createTime":null,"children":[{"hid":2,"higher":1,"content":"跑步","label":null,"status":null,"createTime":null,"children":null},{"hid":3,"higher":1,"content":"游泳","label":null,"status":null,"createTime":null,"children":null},{"hid":4,"higher":1,"content":"射击","label":null,"status":null,"createTime":null,"children":null}]}]}]
     * message : 操作成功
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * hid : 5
         * higher : 0
         * content : 休闲类
         * label : null
         * status : null
         * createTime : null
         * children : [{"hid":1,"higher":5,"content":"运动健身","label":null,"status":null,"createTime":null,"children":[{"hid":2,"higher":1,"content":"跑步","label":null,"status":null,"createTime":null,"children":null},{"hid":3,"higher":1,"content":"游泳","label":null,"status":null,"createTime":null,"children":null},{"hid":4,"higher":1,"content":"射击","label":null,"status":null,"createTime":null,"children":null}]}]
         */

        private int hid;
        private int higher;
        private String content;
        private Object label;
        private Object status;
        private Object createTime;
        private List<ChildrenBeanX> children;

        public int getHid() {
            return hid;
        }

        public void setHid(int hid) {
            this.hid = hid;
        }

        public int getHigher() {
            return higher;
        }

        public void setHigher(int higher) {
            this.higher = higher;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getLabel() {
            return label;
        }

        public void setLabel(Object label) {
            this.label = label;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public List<ChildrenBeanX> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBeanX> children) {
            this.children = children;
        }

        public static class ChildrenBeanX {
            /**
             * hid : 1
             * higher : 5
             * content : 运动健身
             * label : null
             * status : null
             * createTime : null
             * children : [{"hid":2,"higher":1,"content":"跑步","label":null,"status":null,"createTime":null,"children":null},{"hid":3,"higher":1,"content":"游泳","label":null,"status":null,"createTime":null,"children":null},{"hid":4,"higher":1,"content":"射击","label":null,"status":null,"createTime":null,"children":null}]
             */

            private int hid;
            private int higher;
            private String content;
            private Object label;
            private Object status;
            private Object createTime;
            private List<ChildrenBean> children;

            public int getHid() {
                return hid;
            }

            public void setHid(int hid) {
                this.hid = hid;
            }

            public int getHigher() {
                return higher;
            }

            public void setHigher(int higher) {
                this.higher = higher;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Object getLabel() {
                return label;
            }

            public void setLabel(Object label) {
                this.label = label;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public List<ChildrenBean> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBean> children) {
                this.children = children;
            }

            public static class ChildrenBean {
                /**
                 * hid : 2
                 * higher : 1
                 * content : 跑步
                 * label : null
                 * status : null
                 * createTime : null
                 * children : null
                 */

                private int hid;
                private int higher;
                private String content;
                private Object label;
                private Object status;
                private Object createTime;
                private Object children;

                public int getHid() {
                    return hid;
                }

                public void setHid(int hid) {
                    this.hid = hid;
                }

                public int getHigher() {
                    return higher;
                }

                public void setHigher(int higher) {
                    this.higher = higher;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public Object getLabel() {
                    return label;
                }

                public void setLabel(Object label) {
                    this.label = label;
                }

                public Object getStatus() {
                    return status;
                }

                public void setStatus(Object status) {
                    this.status = status;
                }

                public Object getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(Object createTime) {
                    this.createTime = createTime;
                }

                public Object getChildren() {
                    return children;
                }

                public void setChildren(Object children) {
                    this.children = children;
                }
            }
        }
    }
}
