package com.dunbian.jujiabao.mvc;

import org.springframework.web.servlet.ModelAndView;

public enum MVCViewName {

    // @#############
	ADVERTISEMENT_DETAIL("/advertisement/detail"),
	COMMON_BASE("/common/base"),
	COMMON_COMMONJS("/common/commonjs"),
	COMMON_MACRO("/common/macro"),
	COMMON_META("/common/meta"),
	ERROR_NOLOGIN("/error/nologin"),
	FOOTER_FOOTER("/footer/footer"),
	HEADER_HEADER("/header/header"),
	INDEX_ADDRESS("/index/address"),
	INDEX_ADDRESS_ITEM("/index/address_item"),
	INDEX_ADDRESS_LIST("/index/address_list"),
	INDEX_CART("/index/cart"),
	INDEX_CART_CONTENT("/index/cart_content"),
	INDEX_CART_EMPTY("/index/cart_empty"),
	INDEX_CART_ITEM("/index/cart_item"),
	INDEX_COMMENTITEM("/index/comment-item"),
	INDEX_COMMENT("/index/comment"),
	INDEX_DETAIL("/index/detail"),
	INDEX_INDEX("/index/index"),
	INDEX_LEGAL("/index/legal"),
	INDEX_ORDER_DETAIL("/index/order_detail"),
	INDEX_ORDER_SUCCESS("/index/order_success"),
	INDEX_PAYBANK("/index/pay-bank"),
	INDEX_PAYPASSWORD("/index/pay-password"),
	INDEX_PAY_SUCCESS("/index/pay_success"),
	INDEX_STATIC_ADDRESS("/index/static_address"),
	ORDER_ORDERDETAIL("/order/order-detail"),
	ORDER_ORDERITEM("/order/order-item"),
	ORDER_ORDERLIST("/order/orderlist"),
	ORDER_SIDEBAR("/order/sidebar"),
	PROFILE_BANKBILLINGITEM("/profile/bank-billing-item"),
	PROFILE_BANKBILLING("/profile/bank-billing"),
	PROFILE_BANKHEAD("/profile/bank-head"),
	PROFILE_BANKINDEX("/profile/bank-index"),
	PROFILE_BANKPDSET("/profile/bank-pdset"),
	PROFILE_BANKRESETPD("/profile/bank-resetpd"),
	PROFILE_BANKSUCCESS("/profile/bank-success"),
	PROFILE_ORDERCANCEL("/profile/order-cancel"),
	PROFILE_ORDERPAIDED("/profile/order-paided"),
	PROFILE_ORDERUNPAID("/profile/order-unpaid"),
	PROFILE_PROFILE("/profile/profile"),
	PROFILE_SIDEBAR("/profile/sidebar"),
	SITE_ABOUT("/site/about"),
	SITE_BUDING("/site/buding"),
	SITE_CLIENT("/site/client"),
	SITE_WECHAT_DOWNLOAD("/site/wechat_download"),
	UPLOAD_RES("/upload/res");
// @#############

    // 成员变量
    private String viewName;

    // 构造方法
    private MVCViewName(String viewName) {
        this.viewName = viewName;
    }

    // 覆盖方法
    @Override
    public String toString() {
        return this.viewName;
    }

    public ModelAndView replaceView(String templateDir, String version) {
    	return new ModelAndView(this.viewName.replaceAll("/party", "/party/" + templateDir + "/" + version));
    }

    public ModelAndView view() {
    	return new ModelAndView(this.viewName);
    }
}
