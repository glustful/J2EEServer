package com.dunbian.jujiabao.mvc;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dunbian.jujiabao.food.IFoodService;

@Controller
@RequestMapping("/index")
public class IndexController {

	@Resource
	IFoodService foodService;

	@ResponseBody
	@RequestMapping("")
	public Object index() {
		Map<String, Object> tmp = new java.util.HashMap<String, Object>();
		tmp.put("msg", "Hello World,We Are Jujiabao!");
		tmp.put("auth", "puddingnet");
		return tmp;
	}


//	@RequestMapping("/down")
//	public void down(HttpServletResponse response) throws Exception{
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("application/vnd.android.package-archive");
//		response.setHeader("content-disposition", "attachment;filename=jujiabao_upd.apk");
//
//		File f = new File("E:\\sts_workspace\\mobilejuJiaBao\\platforms\\android\\ant-build\\CordovaApp-debug.apk");
//		InputStream is = new FileInputStream(f);
//		byte[] data = new byte[1024];
//		int len = -1;
//
//		while((len = is.read(data)) != -1) {
//			response.getOutputStream().write(data, 0, len);
//		}
//
//		response.flushBuffer();
//	}
}
