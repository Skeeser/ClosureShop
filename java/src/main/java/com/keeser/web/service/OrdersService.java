package com.keeser.web.service;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.common.ResultCode;
import com.keeser.web.common.ResultMetaJson;
import com.keeser.web.dao.GoodsDAO;
import com.keeser.web.dao.OrdersDAO;
import com.keeser.web.dao.OrdersGoodsDAO;
import com.keeser.web.entity.Goods;
import com.keeser.web.entity.Orders;
import com.keeser.web.entity.OrdersGoods;
import com.keeser.web.entity.User;
import jakarta.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrdersService {
    @Autowired
    OrdersDAO ordersDAO;

    @Autowired
    OrdersGoodsDAO ordersGoodsDAO;

    @Autowired
    GoodsDAO goodsDAO;

    // 获取订单列表
    public JSONObject getOrdersList(String query,  int pagenum, int pagessize){
        List<Orders> ordersList = null;

        int allNum = 0;
        try {
            // 模糊搜索获取数量
            allNum = ordersDAO.countAllByOrderNumberLike("%" + query + "%");
            Sort sort = Sort.by(Sort.Direction.DESC, "orderNumber");
            // 要减一保证从零开始
            Pageable pageable = PageRequest.of(pagenum - 1, pagessize, sort);
            ordersList = ordersDAO.findAllByOrderNumberLike("%" + query + "%", pageable);

        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "获取订单列表发生异常").getMetaJson();
        }

        JSONObject retJson = new ResultMetaJson(ResultCode.STATUS_OK, "获取订单列表成功").getMetaJson();
        JSONObject dataJson = new JSONObject();
        dataJson.put("total", allNum);
        dataJson.put("pagenum", pagenum);

        ArrayList<JSONObject> ordersJsonList = new ArrayList<JSONObject>();
        // 生成商品列表
        for(Orders order : ordersList){
            JSONObject orderJson = new JSONObject();
            orderJson.put("order_id", order.getOrderId());
            orderJson.put("user_id", order.getUserId());
            orderJson.put("order_number", order.getOrderNumber());
            orderJson.put("order_price", order.getOrderPrice());
            orderJson.put("order_pay", order.getOrderPay());
            orderJson.put("is_send", order.getIsSend());
            orderJson.put("trade_no", order.getTradeNo());
            orderJson.put("order_fapiao_title", order.getOrderFapiaoTitle());
            orderJson.put("order_fapiao_company", order.getOrderFapiaoCompany());
            orderJson.put("order_fapiao_content", order.getOrderFapiaoContent());
            orderJson.put("consignee_addr", order.getConsigneeAddr());
            orderJson.put("pay_status", order.getPayStatus());
            orderJson.put("create_time", order.getCreateTime());
            orderJson.put("update_time", order.getUpdateTime());
            ordersJsonList.add(orderJson);
        }
        dataJson.put("goods", ordersJsonList);
        retJson.put("data", dataJson);

        return retJson;

    }

    // 获取用户订单列表
    public JSONObject getUserOrdersList(String query){
        List<Orders> ordersList = null;

        int allNum = 0;
        try {
            // 获取当前用户信息
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User)authentication.getPrincipal();
            int user_id = user.getId();


            // 模糊搜索获取数量
            allNum = ordersDAO.countAllByUserIdAndIsCompleteOrderAndOrderNumberLike(user_id, '是', "%" + query + "%");
            Sort sort = Sort.by(Sort.Direction.DESC, "orderNumber");
            ordersList = ordersDAO.findAllByUserIdAndIsCompleteOrderAndOrderNumberLike(user_id, '是', "%" + query + "%");

        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "获取用户订单列表发生异常").getMetaJson();
        }

        JSONObject retJson = new ResultMetaJson(ResultCode.STATUS_OK, "获取用户订单列表成功").getMetaJson();
        JSONObject dataJson = new JSONObject();
        dataJson.put("total", allNum);

        ArrayList<JSONObject> ordersJsonList = new ArrayList<JSONObject>();
        // 生成商品列表
        for(Orders order : ordersList){
            JSONObject orderJson = new JSONObject();
            orderJson.put("order_id", order.getOrderId());
            orderJson.put("user_id", order.getUserId());
            orderJson.put("order_number", order.getOrderNumber());
            orderJson.put("order_price", order.getOrderPrice());
            orderJson.put("order_pay", order.getOrderPay());
            orderJson.put("is_send", order.getIsSend());
            orderJson.put("trade_no", order.getTradeNo());
            orderJson.put("order_fapiao_title", order.getOrderFapiaoTitle());
            orderJson.put("order_fapiao_company", order.getOrderFapiaoCompany());
            orderJson.put("order_fapiao_content", order.getOrderFapiaoContent());
            orderJson.put("consignee_addr", order.getConsigneeAddr());
            orderJson.put("pay_status", order.getPayStatus());
            orderJson.put("create_time", order.getCreateTime());
            orderJson.put("update_time", order.getUpdateTime());
            ordersJsonList.add(orderJson);
        }
        dataJson.put("goods", ordersJsonList);
        retJson.put("data", dataJson);

        return retJson;

    }

    // 插入空的订单并返回订单号
    private  int getNewOders(int user_id){
        try{
            Orders orders = new Orders();
            orders.setUserId(user_id);
            ordersDAO.save(orders);
            return orders.getOrderId();
        }catch (Exception e){
            return 0;
        }
    }

    // 添加购物车
    public JSONObject  addOrdersBuyCar(JSONObject addOdersGoodsJson){

        Orders orders = null;

        try{
            // 获取当前用户信息
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User)authentication.getPrincipal();
            int user_id = user.getId();

            // 先找有没有没付款的订单
            orders = ordersDAO.findByUserIdAndIsCompleteOrder(user_id, '否');
            if(orders == null){
                // 如果没有没付款的, 即购物车为空, 新建
                int orderId = getNewOders(user_id);
                if(orderId == 0) {
                    throw new Exception("创建新订单失败");
                }
                orders = ordersDAO.findByOrderId(orderId);
            }

            List<OrdersGoods> ordersGoodsList = orders.getOrdersGoodsList();
            // 计算购买的商品价格
            Double goods_price  = addOdersGoodsJson.getDouble("goods_price");
            int goods_number = addOdersGoodsJson.getInteger("buy_number");
            OrdersGoods tempOrderGoods = new OrdersGoods();
            tempOrderGoods.setGoodsId(addOdersGoodsJson.getInteger("goods_id"));
            tempOrderGoods.setGoodsPrice(goods_price);
            tempOrderGoods.setGoodsNumber(goods_number);
            double allPrice = goods_price * goods_number;
            tempOrderGoods.setGoodsTotalPrice(allPrice);
            // 增加到订单的价格里
            double orderPrice = orders.getOrderPrice();
            orderPrice += allPrice;
            // 最后一定要设置级联的表为这个, 要不然会保存不成功
            tempOrderGoods.setOrders(orders);

            ordersGoodsList.add(tempOrderGoods);

            // 保存ordersGoods, 而不是oders
            ordersGoodsDAO.save(tempOrderGoods);
        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "添加购物车发生异常").getMetaJson();
        }

        JSONObject retJson = new ResultMetaJson(ResultCode.STATUS_OK, "添加购物车成功").getMetaJson();
        JSONObject dataJson = new JSONObject();
        dataJson.put("orders_id", orders.getOrderId());
        retJson.put("data", dataJson);
        return retJson;
    }

    // 获取购物车的所有信息
    public JSONObject getOrdersBuyCar(){
        Orders orders = null;
        List<OrdersGoods> ordersGoodsList = null;
        ArrayList<JSONObject> dataList = new ArrayList<>();

        try{
            // 获取当前用户信息
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User)authentication.getPrincipal();
            int user_id = user.getId();

            // 先找有没有没付款的订单
            orders = ordersDAO.findByUserIdAndIsCompleteOrder(user_id, '否');
            if(orders == null){
                // 说明为空, 创建新的订单
                int orderId = getNewOders(user_id);
                if(orderId == 0) {
                    throw new Exception("创建新订单失败");
                }
                orders = ordersDAO.findByOrderId(orderId);
            }
            // 获取某个订单的所有商品
            ordersGoodsList = orders.getOrdersGoodsList();
            if(ordersGoodsList != null){
                for(OrdersGoods ordersGoods : ordersGoodsList){
                    JSONObject tempJson = new JSONObject();
                    int good_id =  ordersGoods.getGoodsId();
                    tempJson.put("buycar_id", ordersGoods.getId());
                    tempJson.put("goods_id", good_id);
                    tempJson.put("buy_number", ordersGoods.getGoodsNumber());
                    tempJson.put("all_price", ordersGoods.getGoodsTotalPrice());
                    tempJson.put("goods_price", ordersGoods.getGoodsPrice());
                    // 获取商品的名称信息
                    tempJson.put("goods_name", goodsDAO.findByGoodsId(good_id).getGoodsName());

                    dataList.add(tempJson);
                }
            }

        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "获取购物车发生异常").getMetaJson();
        }

        JSONObject retJson = new ResultMetaJson(ResultCode.STATUS_OK, "获取购物车成功").getMetaJson();
        retJson.put("data", dataList);
        return retJson;
    }


    // 删除购物车的商品
    public JSONObject deleteBuyCarGoodsById(int id){
        try{
            ordersGoodsDAO.deleteById(id);
        }catch (Exception e) {
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST , "删除购物车商品发生异常").getMetaJson();
        }

        return new ResultMetaJson(ResultCode.STATUS_DELETED , "删除购物车商品成功").getMetaJson();
    }

    // 删除某个订单的商品
    public JSONObject deleteOrderById(int id){
        try{
            ordersDAO.deleteById(id);
        }catch (Exception e) {
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST , "删除订单发生异常").getMetaJson();
        }

        return new ResultMetaJson(ResultCode.STATUS_DELETED , "删除订单成功").getMetaJson();
    }


    // 增加订单
    public JSONObject addOrders(JSONObject addOrderJson){
        Orders orders = null;

        JSONObject dataJson = new JSONObject();
        try{
            // 获取当前用户信息
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User)authentication.getPrincipal();
            int user_id = user.getId();

            // 先找有没有没付款的订单
            orders = ordersDAO.findByUserIdAndIsCompleteOrder(user_id, '否');
            if(orders == null){
                throw new Exception("获取购物车失败, 可能为空");
            }
            dataJson.put("order_id", orders.getOrderId());
            // 更新订单信息
            orders.setOrderPay(addOrderJson.getString("order_pay").charAt(0));
            orders.setOrderPrice(addOrderJson.getDouble("order_price"));
            // 更新别的信息
            // 设置订单结算
            orders.setIsCompleteOrder('是');
            // 设置更新时间
            orders.setUpdateTime(new Date().getTime() / 1000 );

            // 更新对应商品的信息
            List<OrdersGoods> ordersGoodsList = orders.getOrdersGoodsList();
            for(OrdersGoods ordersGoods: ordersGoodsList){
                // 获取商品id
                int good_id = ordersGoods.getGoodsId();
                // 获取商品对象
                Goods goods = goodsDAO.findByGoodsId(good_id);
                // 更新商品属性
                goods.setUpdTime(new Date().getTime() / 1000);
                // 更新热键
                goods.setHotNumber(goods.getHotNumber() + 1);
                // 商品剩余数量
                int resGoodsNumber =  goods.getGoodsNumber() - ordersGoods.getGoodsNumber();
                if(resGoodsNumber < 0){
                    JSONObject retJson = new ResultMetaJson(ResultCode.STATUS_INTERNAL_SERVER_ERROR, "商品数量不足").getMetaJson();
                    // 返回数量不足信息
                    JSONObject tempJson = new JSONObject();
                    tempJson.put("good_id", good_id);
                    tempJson.put("good_name", goods.getGoodsName());
                    retJson.put("error", tempJson);
                    return retJson;
                }
                // 更新数量
                goods.setGoodsNumber(resGoodsNumber);
                goodsDAO.save(goods);
            }


            ordersDAO.save(orders);

        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "添加订单发生异常").getMetaJson();
        }

        JSONObject retJson = new ResultMetaJson(ResultCode.STATUS_CREATED, "添加订单成功").getMetaJson();
        retJson.put("data", dataJson);
        return retJson;
    }
}
