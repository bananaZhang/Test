package bean;

public class JustBean {

    private String orderNum;
    private String user;
    private String receiveAddr;
    private String telphone;
    private Integer count;

    public JustBean() {
        super();
    }

    public JustBean(String orderNum, String user, String receiveAddr, String telphone, Integer count) {
        super();
        this.orderNum = orderNum;
        this.user = user;
        this.receiveAddr = receiveAddr;
        this.telphone = telphone;
        this.count = count;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getReceiveAddr() {
        return receiveAddr;
    }

    public void setReceiveAddr(String receiveAddr) {
        this.receiveAddr = receiveAddr;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "JustBean [orderNum=" + orderNum + ", user=" + user + ", receiveAddr=" + receiveAddr + ", telphone="
                + telphone + ", count=" + count + "]";
    }

}
