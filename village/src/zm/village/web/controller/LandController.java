package zm.village.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zm.village.dao.Land;
import zm.village.dao.User;
import zm.village.service.LandService;
import zm.village.util.tools.HttpReturn;

/**
 * @author 伍伴
 * @Date 2018年7月21日
 * @Description 安卓前端土地控制层
 * @version 1.0
 */

@Controller
@RequestMapping("/Land")
public class LandController {

	@Autowired
	private LandService service;

	@RequestMapping(value = "/getAll", method = RequestMethod.POST)
	public void getAll(HttpServletResponse response, int start, int end) throws IOException {

		List<Land> lands = service.selectAll();
		List<Land> vo = new LinkedList<Land>();
		if (start < lands.size()) {
			if (end > lands.size()) {
				for (int i = start; i < lands.size(); i++) {
					vo.add(lands.get(i));
				}
			}
			else {
				for (int i = start; i < end; i++) {
					vo.add(lands.get(i));
				}
			}
		}
		@SuppressWarnings("static-access")
		JSONArray jsonArray = new JSONArray().fromObject(vo);

		HttpReturn.reponseBody(response, jsonArray);
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public void get(HttpServletResponse response, @RequestBody Land vo) throws IOException {

		@SuppressWarnings("static-access")
		JSONObject jsonObject = new JSONObject().fromObject(service.select(vo));
		HttpReturn.reponseBody(response, jsonObject);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(HttpServletResponse response, @RequestBody Land vo
			) throws IOException {

		service.insert(vo);
		HttpReturn.reponseBody(response, "添加成功");
		
	}

	@RequestMapping(value = "/addImage", method = RequestMethod.POST)
	public void addImage(HttpServletResponse response, @RequestBody Land vo,@RequestParam(name="imgURL", required = false) MultipartFile[] file1,
			@RequestParam(name="certificateURL", required = false) 
	MultipartFile[] file2
			) throws IOException {

		System.out.println(vo);
		System.out.println(file1.length);
		if(file1 != null && file1.length > 0){
			String	upaloadUrl	= System.getProperty( "evan.webappvillage" ) + "..\\img\\";
			String	img_url = upaloadUrl;
			File  dir  = new File(upaloadUrl);                                                 
			Format	format	= new SimpleDateFormat( "yyyyMMdd" );
			String	data	= "";
			int	intFlag = 0;
			String	image	= "";
			String	time	= "";
			String  img_url1 ="";
			if ( !dir.exists() )                                                           
				dir.mkdirs();
			for (int i = 0; i < file1.length; i++ ){
				
			MultipartFile file = file1[i];
			data	= format.format( new Date() );
			intFlag	= (int) (Math.random() * 1000000);
			image	= String.valueOf( intFlag );
			time	= String.valueOf( new Date().getTime() );       
			img_url=img_url1;
			img_url	= img_url + data;
			upaloadUrl	= upaloadUrl + data;
			File tagetFile = new File( img_url );                        
			if ( !tagetFile.isDirectory() )
					tagetFile.mkdirs();
			upaloadUrl = upaloadUrl + "\\" + time + image + ".jpg";
			File tagetFile2 = new File( upaloadUrl );                       
			if ( !tagetFile2.exists() ) {                                   
					try {
						tagetFile.createNewFile();
					} catch ( IOException e ) {
						e.printStackTrace();
					}
					try {
						file.transferTo( tagetFile2 );
					} catch ( IllegalStateException e ) {
						e.printStackTrace();
					} catch ( IOException e ) {
						e.printStackTrace();
					}
			}
			 if(file1.length>1 && i<file1.length-1)
			  upaloadUrl += "&";
			}
			vo.setImgURL(upaloadUrl);
		}
		if(file2 != null && file2.length > 0){
			String	upaloadUrl	= System.getProperty( "evan.webappvillage" ) + "..\\img\\";
			String	img_url = upaloadUrl;
			String  img_url1 ="";
			File  dir  = new File(upaloadUrl);                                                  
			Format	format	= new SimpleDateFormat( "yyyyMMdd" );
			String	data	= "";
			int	intFlag = 0;
			String	image	= "";
			String	time	= "";
			if ( !dir.exists() )                                                           
				dir.mkdirs();
			for (int i = 0; i < file2.length; i++ ){
				
			MultipartFile file = file2[i];
			data	= format.format( new Date() );
			intFlag	= (int) (Math.random() * 1000000);
			image	= String.valueOf( intFlag );
			time	= String.valueOf( new Date().getTime() );       
			img_url=img_url1;
			img_url	= img_url + data;
			upaloadUrl	= upaloadUrl + data;
			File tagetFile = new File( img_url );                       
			if ( !tagetFile.isDirectory() )
					tagetFile.mkdirs();
			upaloadUrl = upaloadUrl + "\\" + time + image + ".jpg";
			File tagetFile2 = new File( upaloadUrl );                       
			if ( !tagetFile2.exists() ) {                                 
					try {
						tagetFile.createNewFile();
					} catch ( IOException e ) {
						e.printStackTrace();
					}
					try {
						file.transferTo( tagetFile2 );
					} catch ( IllegalStateException e ) {
						e.printStackTrace();
					} catch ( IOException e ) {
						e.printStackTrace();
					}
			}
			 if(file2.length>1 && i<file2.length-1)
			  upaloadUrl += "&";
			}
			vo.setCertificateURL(upaloadUrl);
		}
		//service.insert(vo);
		HttpReturn.reponseBody(response, "添加成功");
		
	}

	@RequestMapping(value = "/updateImage", method = RequestMethod.POST)
	public void updateImage(HttpServletRequest request,HttpServletResponse response,@RequestParam("imgURL") 
	MultipartFile[] file1,@RequestParam("certificateURL") MultipartFile[] file2) throws IOException {

		String Id = request.getParameter("id");
		int id = Integer.parseInt(Id);
		Land vo = service.getById(id);
		if(file1 != null && file1.length > 0){
			String	upaloadUrl	= System.getProperty( "evan.webappvillage" ) + "..\\img\\";
			String	img_url = upaloadUrl;
			File  dir  = new File(upaloadUrl);                                                 
			Format	format	= new SimpleDateFormat( "yyyyMMdd" );
			String	data	= "";
			int	intFlag = 0;
			String	image	= "";
			String	time	= "";
			String  img_url1 ="";
			if ( !dir.exists() )                                                           
				dir.mkdirs();
			for (int i = 0; i < file1.length; i++ ){
				
			MultipartFile file = file1[i];
			data	= format.format( new Date() );
			intFlag	= (int) (Math.random() * 1000000);
			image	= String.valueOf( intFlag );
			time	= String.valueOf( new Date().getTime() );       
			upaloadUrl=img_url;
			upaloadUrl	= upaloadUrl + data;
			File tagetFile = new File( upaloadUrl );                        
			if ( !tagetFile.isDirectory() )
					tagetFile.mkdirs();
			upaloadUrl = upaloadUrl + "\\" + time + image + ".jpg";
			File tagetFile2 = new File( upaloadUrl );                       
			if ( !tagetFile2.exists() ) {                                   
					try {
						tagetFile.createNewFile();
					} catch ( IOException e ) {
						e.printStackTrace();
					}
					try {
						file.transferTo( tagetFile2 );
					} catch ( IllegalStateException e ) {
						e.printStackTrace();
					} catch ( IOException e ) {
						e.printStackTrace();
					}
			}
			img_url1 +="../img/"+data+"/"+time + image + ".jpg";
			 if(file1.length>1 && i<file1.length-1)
				 img_url1 += "&";
			}
			vo.setImgURL(img_url1);
		}
		if(file2 != null && file2.length > 0){
			String	upaloadUrl	= System.getProperty( "evan.webappvillage" ) + "..\\img\\";
			String	img_url = upaloadUrl;
			String  img_url1 ="";
			File  dir  = new File(upaloadUrl);                                                  
			Format	format	= new SimpleDateFormat( "yyyyMMdd" );
			String	data	= "";
			int	intFlag = 0;
			String	image	= "";
			String	time	= "";
			if ( !dir.exists() )                                                           
				dir.mkdirs();
			for (int i = 0; i < file2.length; i++ ){
				
			MultipartFile file = file2[i];
			data	= format.format( new Date() );
			intFlag	= (int) (Math.random() * 1000000);
			image	= String.valueOf( intFlag );
			time	= String.valueOf( new Date().getTime() );       
			upaloadUrl=img_url;
			upaloadUrl	= upaloadUrl + data;
			File tagetFile = new File( upaloadUrl );                       
			if ( !tagetFile.isDirectory() )
					tagetFile.mkdirs();
			upaloadUrl = upaloadUrl + "\\" + time + image + ".jpg";
			File tagetFile2 = new File( upaloadUrl );                       
			if ( !tagetFile2.exists() ) {                                 
					try {
						tagetFile.createNewFile();
					} catch ( IOException e ) {
						e.printStackTrace();
					}
					try {
						file.transferTo( tagetFile2 );
					} catch ( IllegalStateException e ) {
						e.printStackTrace();
					} catch ( IOException e ) {
						e.printStackTrace();
					}
			}
			img_url1 +="../img/"+data+"/"+time + image + ".jpg";
			 if(file2.length>1 && i<file2.length-1)
				 img_url1 += "&";
			}
			vo.setCertificateURL(img_url1);
		}
		HttpReturn.reponseBody(response, "修改成功");
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletResponse response, @RequestBody Land vo) throws IOException {

		service.update(vo);
		HttpReturn.reponseBody(response, "修改成功");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletResponse response, @RequestBody Land vo) throws IOException {

		service.delete(vo);
		HttpReturn.reponseBody(response, "删除成功");
	}
}
