package koala.weibo.bean;

/**
 * Created by taoxj on 15-11-25.
 */
public class UserBean {
    private String id;                    //�û�UID
    private String screen_name;           //�û��ǳ�
    private String name;                  //�Ѻ���ʾ����
    private String province; 	              //�û�����ʡ��ID
    private String city; 	                  //�û����ڳ���ID
    private String location; 	          //�û����ڵ�
    private String description; 	      //�û���������
    private String url; 	              //�û����͵�ַ
    private String profile_image_url;     //�û�ͷ���ַ����ͼ����50��50����
    private String profile_url; 	      //�û���΢��ͳһURL��ַ
    private String domain ;	              //�û��ĸ��Ի�����
    private String weihao ;	              //�û���΢��
    private String gender ;               //�Ա�m���С�f��Ů��n��δ֪
    private String followers_count = "0" ;	      //��˿��
    private String friends_count = "0";	          //��ע��
    private String statuses_count = "0";	      //΢����
    private String favourites_count = "0";        //�ղ���
    private String created_at ;	          //�û�������ע�ᣩʱ��
    private boolean allow_all_act_msg ;   //�Ƿ����������˸��ҷ�˽�ţ�true���ǣ�false����
    private boolean geo_enabled ;	      //�Ƿ������ʶ�û��ĵ���λ�ã�true���ǣ�false����
    private boolean verified ;	          //�Ƿ���΢����֤�û�������V�û���true���ǣ�false����
    private int verified_type ;	          //��δ֧��
    private String remark ;	              //�û���ע��Ϣ��ֻ���ڲ�ѯ�û���ϵʱ�ŷ��ش��ֶ�
    private boolean allow_all_comment ;   //�Ƿ����������˶��ҵ�΢���������ۣ�true���ǣ�false����
    private String avatar_large ;	      //�û�ͷ���ַ����ͼ����180��180����
    private String  verified_reason ;     //��֤ԭ��
    private boolean follow_me ;  	      //���û��Ƿ��ע��ǰ��¼�û���true���ǣ�false����
    private String online_status ;	          //�û�������״̬��0�������ߡ�1������
    private String bi_followers_count ;	  //�û��Ļ�����


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getWeihao() {
        return weihao;
    }

    public void setWeihao(String weihao) {
        this.weihao = weihao;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(String followers_count) {
        this.followers_count = followers_count;
    }

    public String getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(String friends_count) {
        this.friends_count = friends_count;
    }

    public String getStatuses_count() {
        return statuses_count;
    }

    public void setStatuses_count(String statuses_count) {
        this.statuses_count = statuses_count;
    }

    public String getFavourites_count() {
        return favourites_count;
    }

    public void setFavourites_count(String favourites_count) {
        this.favourites_count = favourites_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isAllow_all_act_msg() {
        return allow_all_act_msg;
    }

    public void setAllow_all_act_msg(boolean allow_all_act_msg) {
        this.allow_all_act_msg = allow_all_act_msg;
    }

    public boolean isGeo_enabled() {
        return geo_enabled;
    }

    public void setGeo_enabled(boolean geo_enabled) {
        this.geo_enabled = geo_enabled;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getVerified_type() {
        return verified_type;
    }

    public void setVerified_type(int verified_type) {
        this.verified_type = verified_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isAllow_all_comment() {
        return allow_all_comment;
    }

    public void setAllow_all_comment(boolean allow_all_comment) {
        this.allow_all_comment = allow_all_comment;
    }

    public String getAvatar_large() {
        return avatar_large;
    }

    public void setAvatar_large(String avatar_large) {
        this.avatar_large = avatar_large;
    }

    public String getVerified_reason() {
        return verified_reason;
    }

    public void setVerified_reason(String verified_reason) {
        this.verified_reason = verified_reason;
    }

    public boolean isFollow_me() {
        return follow_me;
    }

    public void setFollow_me(boolean follow_me) {
        this.follow_me = follow_me;
    }

    public String getOnline_status() {
        return online_status;
    }

    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }

    public String getBi_followers_count() {
        return bi_followers_count;
    }

    public void setBi_followers_count(String bi_followers_count) {
        this.bi_followers_count = bi_followers_count;
    }
}
