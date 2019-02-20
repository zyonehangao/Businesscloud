package com.cloud.shangwu.businesscloud.mvp.model.bean;

import java.util.List;

public class ToDoListBean {
    /**
     * code : 200
     * data : {"list":[{"uid":15,"email":"asfhwuef234@qq.com","clan":null,"name":null,"telephone":null,"companyName":null,"password":null,"salt":null,"personalCode":null,"invitedCode":null,"area":null,"portrait":"201901240839055942.png","hobbys":null,"label":null,"position":null,"intro":null,"businessScope":null,"impact":null,"goal":null,"im":null,"qq":null,"wx":null,"type":null,"status":null,"registerTime":null,"username":"4396","pid":null,"country":null,"userno":null,"nickname":null},{"uid":12,"email":"fa@we.fddw","clan":null,"name":null,"telephone":null,"companyName":null,"password":null,"salt":null,"personalCode":null,"invitedCode":null,"area":null,"portrait":null,"hobbys":null,"label":null,"position":null,"intro":null,"businessScope":null,"impact":null,"goal":null,"im":null,"qq":null,"wx":null,"type":null,"status":null,"registerTime":null,"username":"cometSsb2","pid":null,"country":null,"userno":null,"nickname":null},{"uid":12,"email":"fa@we.fddw","clan":null,"name":null,"telephone":null,"companyName":null,"password":null,"salt":null,"personalCode":null,"invitedCode":null,"area":null,"portrait":null,"hobbys":null,"label":null,"position":null,"intro":null,"businessScope":null,"impact":null,"goal":null,"im":null,"qq":null,"wx":null,"type":null,"status":null,"registerTime":null,"username":"cometSsb2","pid":null,"country":null,"userno":null,"nickname":null},{"uid":8,"email":"a132@163.com","clan":null,"name":null,"telephone":"15717818788","companyName":null,"password":null,"salt":null,"personalCode":null,"invitedCode":null,"area":null,"portrait":null,"hobbys":null,"label":null,"position":null,"intro":null,"businessScope":null,"impact":null,"goal":null,"im":null,"qq":null,"wx":null,"type":null,"status":null,"registerTime":null,"username":"abc123","pid":null,"country":null,"userno":null,"nickname":null},{"uid":16,"email":"dfsf23423@qq.com","clan":null,"name":null,"telephone":null,"companyName":null,"password":null,"salt":null,"personalCode":null,"invitedCode":null,"area":null,"portrait":"201901240839055942.png","hobbys":null,"label":null,"position":null,"intro":null,"businessScope":null,"impact":null,"goal":null,"im":null,"qq":null,"wx":null,"type":null,"status":null,"registerTime":null,"username":"4397","pid":null,"country":null,"userno":null,"nickname":null}],"pageInfo":{"pageSize":10,"pageCurrent":1,"recordCount":5,"pageCount":1}}
     * message : 操作成功
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * list : [{"uid":15,"email":"asfhwuef234@qq.com","clan":null,"name":null,"telephone":null,"companyName":null,"password":null,"salt":null,"personalCode":null,"invitedCode":null,"area":null,"portrait":"201901240839055942.png","hobbys":null,"label":null,"position":null,"intro":null,"businessScope":null,"impact":null,"goal":null,"im":null,"qq":null,"wx":null,"type":null,"status":null,"registerTime":null,"username":"4396","pid":null,"country":null,"userno":null,"nickname":null},{"uid":12,"email":"fa@we.fddw","clan":null,"name":null,"telephone":null,"companyName":null,"password":null,"salt":null,"personalCode":null,"invitedCode":null,"area":null,"portrait":null,"hobbys":null,"label":null,"position":null,"intro":null,"businessScope":null,"impact":null,"goal":null,"im":null,"qq":null,"wx":null,"type":null,"status":null,"registerTime":null,"username":"cometSsb2","pid":null,"country":null,"userno":null,"nickname":null},{"uid":12,"email":"fa@we.fddw","clan":null,"name":null,"telephone":null,"companyName":null,"password":null,"salt":null,"personalCode":null,"invitedCode":null,"area":null,"portrait":null,"hobbys":null,"label":null,"position":null,"intro":null,"businessScope":null,"impact":null,"goal":null,"im":null,"qq":null,"wx":null,"type":null,"status":null,"registerTime":null,"username":"cometSsb2","pid":null,"country":null,"userno":null,"nickname":null},{"uid":8,"email":"a132@163.com","clan":null,"name":null,"telephone":"15717818788","companyName":null,"password":null,"salt":null,"personalCode":null,"invitedCode":null,"area":null,"portrait":null,"hobbys":null,"label":null,"position":null,"intro":null,"businessScope":null,"impact":null,"goal":null,"im":null,"qq":null,"wx":null,"type":null,"status":null,"registerTime":null,"username":"abc123","pid":null,"country":null,"userno":null,"nickname":null},{"uid":16,"email":"dfsf23423@qq.com","clan":null,"name":null,"telephone":null,"companyName":null,"password":null,"salt":null,"personalCode":null,"invitedCode":null,"area":null,"portrait":"201901240839055942.png","hobbys":null,"label":null,"position":null,"intro":null,"businessScope":null,"impact":null,"goal":null,"im":null,"qq":null,"wx":null,"type":null,"status":null,"registerTime":null,"username":"4397","pid":null,"country":null,"userno":null,"nickname":null}]
         * pageInfo : {"pageSize":10,"pageCurrent":1,"recordCount":5,"pageCount":1}
         */

        private PageInfoBean pageInfo;
        private List<ListBean> list;

        public PageInfoBean getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(PageInfoBean pageInfo) {
            this.pageInfo = pageInfo;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageInfoBean {
            /**
             * pageSize : 10
             * pageCurrent : 1
             * recordCount : 5
             * pageCount : 1
             */

            private int pageSize;
            private int pageCurrent;
            private int recordCount;
            private int pageCount;

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getPageCurrent() {
                return pageCurrent;
            }

            public void setPageCurrent(int pageCurrent) {
                this.pageCurrent = pageCurrent;
            }

            public int getRecordCount() {
                return recordCount;
            }

            public void setRecordCount(int recordCount) {
                this.recordCount = recordCount;
            }

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }
        }

        public static class ListBean {
            /**
             * uid : 15
             * email : asfhwuef234@qq.com
             * clan : null
             * name : null
             * telephone : null
             * companyName : null
             * password : null
             * salt : null
             * personalCode : null
             * invitedCode : null
             * area : null
             * portrait : 201901240839055942.png
             * hobbys : null
             * label : null
             * position : null
             * intro : null
             * businessScope : null
             * impact : null
             * goal : null
             * im : null
             * qq : null
             * wx : null
             * type : null
             * status : null
             * registerTime : null
             * username : 4396
             * pid : null
             * country : null
             * userno : null
             * nickname : null
             */

            private int uid;
            private String email;
            private String clan;
            private String name;
            private String telephone;
            private String companyName;
            private String password;
            private String salt;
            private String personalCode;
            private String invitedCode;
            private String area;
            private String portrait;
            private String hobbys;
            private String label;
            private String position;
            private String intro;
            private String businessScope;
            private String impact;
            private String goal;
            private String im;
            private String qq;
            private String wx;
            private String type;
            private String status;
            private String registerTime;
            private String username;
            private String pid;
            private String country;
            private String userno;
            private String nickname;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getClan() {
                return clan;
            }

            public void setClan(String clan) {
                this.clan = clan;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getSalt() {
                return salt;
            }

            public void setSalt(String salt) {
                this.salt = salt;
            }

            public String getPersonalCode() {
                return personalCode;
            }

            public void setPersonalCode(String personalCode) {
                this.personalCode = personalCode;
            }

            public String getInvitedCode() {
                return invitedCode;
            }

            public void setInvitedCode(String invitedCode) {
                this.invitedCode = invitedCode;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getPortrait() {
                return portrait;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public String getHobbys() {
                return hobbys;
            }

            public void setHobbys(String hobbys) {
                this.hobbys = hobbys;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getBusinessScope() {
                return businessScope;
            }

            public void setBusinessScope(String businessScope) {
                this.businessScope = businessScope;
            }

            public String getImpact() {
                return impact;
            }

            public void setImpact(String impact) {
                this.impact = impact;
            }

            public String getGoal() {
                return goal;
            }

            public void setGoal(String goal) {
                this.goal = goal;
            }

            public String getIm() {
                return im;
            }

            public void setIm(String im) {
                this.im = im;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getWx() {
                return wx;
            }

            public void setWx(String wx) {
                this.wx = wx;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getRegisterTime() {
                return registerTime;
            }

            public void setRegisterTime(String registerTime) {
                this.registerTime = registerTime;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getUserno() {
                return userno;
            }

            public void setUserno(String userno) {
                this.userno = userno;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }
    }
}
