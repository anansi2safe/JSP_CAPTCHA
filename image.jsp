<%@ page language="java" contentType="image/jpeg; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,javax.imageio.ImageIO,javax.servlet.*,javax.servlet.http.*" %>
<%@ page import="javax.servlet.annotation.WebServlet,java.awt.*,java.awt.image.*,java.io.IOException" %>
<%
//设置脚本返回content-type为image/jpeg
response.setContentType("image/jpeg");
//设置图片的宽和高
int width=300,height=100;
//设置验证码字符串字体
Font font_s=new Font(null,Font.ITALIC,50);
//设置验证码字符种子，因为0与o太像，所以把0排除
String str_z="ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
//在内存中加载一张图片
BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
//设置画笔
Graphics2D g=(Graphics2D)image.getGraphics();
//绘制背景色
g.setColor(Color.ORANGE);
g.fillRect(0, 0, width, height);

//绘制字体与边框颜色,并且设置字体
g.setColor(Color.RED);
g.drawRect(0, 0, width-1, height-1);
g.setFont(font_s);
//随机数
Random rang=new Random();
StringBuilder str_builder=new StringBuilder();
//验证码是由4个字符组成， 所以循环四次
for(int i=0;i<4;i++){
	int index=rang.nextInt(str_z.length());
	str_builder.append(str_z.charAt(index));
	//绘制字符串
	g.drawString(" "+str_z.charAt(index), width/5*i, height/2);
}
String yz_code=str_builder.toString();
//设置session以便于验证后 台脚本验证验证码的正确性
request.getSession().setAttribute("code", yz_code);

//绘制视觉干扰线,19条红色
g.setColor(Color.red);
for(int i=0;i<19;i++){
	int x1=rang.nextInt(width);
	int x2=rang.nextInt(width);
	
	int y1=rang.nextInt(height);
	int y2=rang.nextInt(height);
	
	g.drawLine(x1,y1,x2,y2);
}
//完成图片
g.dispose();
ServletOutputStream stm=response.getOutputStream();
//将验证码图片数据以数据流形式返回
ImageIO.write(image, "JPEG", stm);
//关闭输入流
stm.flush();
stm.close();
%>