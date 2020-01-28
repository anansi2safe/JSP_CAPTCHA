package cn.mq.img;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//基于servlet实现的图片验证码
//验证码图片资源生成servlet
@WebServlet("/Image.do")
public class Image extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		int width=400;
		int height=130;
		Font font=new Font(null,Font.ITALIC,70);
		String s_code="ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g=(Graphics2D)image.getGraphics();
		
		//绘制背景色
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, width, height);
		
		//绘制边框与字体颜色
		g.setColor(Color.RED);
		g.drawRect(0, 0, width-1, height-1);
		g.setFont(font);
		
		//随机数
		Random r=new Random();
		StringBuilder str_code=new StringBuilder();
		for(int i=0;i<4;i++) {
			int index=r.nextInt(s_code.length());
			str_code.append(s_code.charAt(index));
			//在图片上绘制字符串，并设置随即字符出现的高度
			g.drawString(" "+s_code.charAt(index), width/5*i, height/2+i+index+5);
		}
		//将验证码存入session，以便注册过滤器验证
		request.getSession().setAttribute("code", str_code.toString());
		
		//绘制视觉干扰线
		g.setColor(Color.RED);
		for(int i=0;i<60;i++) {
			int x1=r.nextInt(width);
			int y1=r.nextInt(height);
			
			int x2=r.nextInt(width);
			int y2=r.nextInt(height);
			g.drawLine(x1, y1, x2, y2);
		}
		//完成图片
		g.dispose();
		//获取输入流，并将绘制好的图片输入
		ServletOutputStream stm=response.getOutputStream();
		ImageIO.write(image, "JPEG", stm);
		
		//刷新输入流并关闭，确保图片输出完整
		stm.flush();
		stm.close();
	}

}
