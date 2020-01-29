package com.ssm.OaManager.test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import com.ssm.OaManager.entity.Announcement;
import com.ssm.OaManager.mydao.RuMiddle;
import com.ssm.OaManager.mydao.RuMiddleService;
import com.ssm.OaManager.service.system.AnnouncementService;


public class Test {

//	@Autowired
//	private TableTestService tableTestService;

	//测试spring整合
	@org.junit.Test
	public void test02() {
		
//		ApplicationContext ctx=new ClassPathXmlApplicationContext("beans.xml");  
//		System.out.println(ctx);
//		//获得bean
//		//TableTestDao tableTestDao=ctx.getBean("tableTestDao",TableTestDao.class);
//		TableTestService tableTestService = ctx.getBean("tableTestService",TableTestService.class);
//		System.out.println("这里不行哈哈");  
//		//访问数据库
//		List<TableTest> lists = tableTestService.findAll(); 
//		for (TableTest tableTest : lists) {	
//			System.out.println(tableTest);
//		}    

		ApplicationContext ctx=new ClassPathXmlApplicationContext("beans.xml");  
		System.out.println(ctx);
		//获得bean
		//TableTestDao tableTestDao=ctx.getBean("tableTestDao",TableTestDao.class);
		RuMiddleService ruMiddleService = ctx.getBean("ruMiddleService",RuMiddleService.class);
		System.out.println("这里不行哈哈");  
		//访问数据库
		List<RuMiddle> lists = ruMiddleService.findAll(); 
		for (RuMiddle ruMiddle : lists) {	
			System.out.println(ruMiddle);
		}    
		
		
		
	}
	
	
	@org.junit.Test
	public void findgameById() {
//		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
//		AnnouncementService service = (AnnouncementService) ac
//				.getBean("announcementServiceImpl");
//		
//		List<Announcement>  announcements = service.findByNewnotice();
//		
//		for(Announcement announcement:announcements){
//			System.out.println(announcement.getAmConten());
//		}
		
//		 String timeStamp = "1531782000000";//直接是时间戳
//         // long timeStamp = System.currentTimeMillis();  //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
//         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
//         String sd = sdf.format(new Date(timeStamp));   // 时间戳转换成时间
//         System.out.println(sd);//打印出你要的时间
	}
}

