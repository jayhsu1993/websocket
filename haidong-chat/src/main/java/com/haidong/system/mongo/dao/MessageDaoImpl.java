package com.haidong.system.mongo.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.haidong.system.mongo.PageConst;
import com.haidong.system.mongo.entity.ChatMessage;
import com.haidong.system.mongo.entity.SpringDataPageable;
@Repository
public class MessageDaoImpl implements MessageDao{

    @Autowired
    protected MongoOperations mongoOperations;
    
    //增
    public String save(ChatMessage message){
    	message.setCreateTime(new Date());
        mongoOperations.insert(message);
        return message.getId();
    }
    
    //查
    public List<ChatMessage> getByRoomId(String roomId){
    	List<ChatMessage> result = mongoOperations.find(new Query(where("roomId").is(roomId)), ChatMessage.class);
        return result;
    }
    
    //删
    public void removeByRoomId(String roomId){
    	mongoOperations.remove(
        		new Query(Criteria.where( "roomId" ).is(roomId)),
        		ChatMessage.class );
    }

    //分页
	public Page<ChatMessage> paginationQuery(String roomId ,Integer pageNum, Integer pageSize) {
	    SpringDataPageable pageable = new SpringDataPageable();
	    Query query = new Query(Criteria.where( "roomId" ).is(roomId));
	    List<Order> orders = new ArrayList<Order>();  //排序
	    orders.add(new Order(Direction.DESC, "createTime"));
	    Sort sort = new Sort(orders);

	    // 开始页
	    pageable.setPagenumber(pageNum);
	    // 每页条数
	    if(pageSize == null || pageSize == 0){
	    	pageable.setPagesize(PageConst.DEFAULT_ITEMS_PER_PAGE);
	    }else{
	    	pageable.setPagesize(pageSize);
	    }
	  
	    // 排序
	    pageable.setSort(sort);
	    // 查询出一共的条数
	    Long count = mongoOperations.count(query, ChatMessage.class);
	    // 查询
	    List<ChatMessage> list = mongoOperations.find(query.with(pageable), ChatMessage.class);
	    // 将集合与分页结果封装
	    Page<ChatMessage> pagelist = new PageImpl<ChatMessage>(list, pageable, count);
	    return pagelist;
	}

}
