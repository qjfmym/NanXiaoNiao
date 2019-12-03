package com.itheima.daoImpl;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;
import com.alibaba.fastjson.JSON;
import com.itheima.dao.CommentDao;
import com.itheima.domain.Comment;
import com.itheima.domain.CommentAnduser;
import com.itheima.utils.DataSourceUtils;

public class CommentDaoImpl implements CommentDao {
	private QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

	public void addComment(Comment commentObj) throws Exception {
		String sql = "INSERT into tab_comment values(?,?,?,?,?)";
		Object [] parames = {null,commentObj.getRid(),commentObj.getUid(),commentObj.getComment(),commentObj.getCraeteDdate()};
		runner.update(sql,parames);
	}

	
	public List<CommentAnduser> findAllComment(int rid) throws Exception {
		  String sql ="select  com.comment,com.craeteDdate ,us.image,us.name FROM tab_user us ,tab_comment com where us.uid = com.uid and rid = ?";
	      List<CommentAnduser> query = runner.query(sql, new BeanListHandler<CommentAnduser>(CommentAnduser.class),111);
	   
      return query;
	}
	
	
	public void fun() throws Exception {
		  String sql ="select  com.comment,com.craeteDdate ,us.image,us.name FROM tab_user us ,tab_comment com where us.uid = com.uid and rid = ?";
	      List<CommentAnduser> query = runner.query(sql, new BeanListHandler<CommentAnduser>(CommentAnduser.class),111);
	      String jsonString = JSON.toJSONString(query);
	      System.out.println(jsonString);
	}

	public int getCountComment(int rid) throws Exception {
		String sql = "select count(*) from tab_comment where rid= ?";
		Long query = (Long) runner.query(sql, new ScalarHandler(),rid);
		return query.intValue();
	}

	public List<CommentAnduser> findPageDate(int indexNumber, int pageSize,int rid) throws Exception {
		String sql = "select  com.comment,com.craeteDdate ,us.image,us.name FROM tab_user us ,tab_comment com where us.uid = com.uid and rid =?  ORDER BY craeteDdate DESC limit ?,?";
		List<CommentAnduser> query = runner.query(sql, new BeanListHandler<CommentAnduser>(CommentAnduser.class),rid,indexNumber,pageSize);
		return query;
	}
	
}
