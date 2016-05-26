package com.dph.system.sys.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dph.common.web.BaseController;
import com.dph.system.shiro.filter.ValidationCodeFilter;

@Controller
@RequestMapping("sys")
public class SysController extends BaseController {

	@Value("${shiro.retryLoginLimit}")
	private int retryLoginLimit = 5;

	// 验证码图片的宽度
	private int width = 63;
	// 验证码图片的高度
	private int height = 20;
	// 验证码字符个数
	private int codeCount = 4;
	// 字体高度
	private int fontHeight = height - 1;
	// 验证码随机数字
	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	@RequestMapping("login")
	public String loginView(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String destPage = "system/sys/login";

		String errorClassName = (String) request.getAttribute("shiroLoginFailure");
		if ("validation_error".equalsIgnoreCase(errorClassName)) {
			request.setAttribute("error", "验证码错误！");
		} else if (UnknownAccountException.class.getName().equals(errorClassName)) {
			request.setAttribute("error", "用户名/密码错误!");
		} else if (IncorrectCredentialsException.class.getName().equals(errorClassName)) {
			request.setAttribute("error", "用户名/密码错误!");
		} else if (LockedAccountException.class.getName().equals(errorClassName)) {
			request.setAttribute("error", "用户已被锁定，请联系管理员!");
		} else if (ExcessiveAttemptsException.class.getName().equals(errorClassName)) {
			request.setAttribute("error", "登录失败已超过" + retryLoginLimit + "次，请稍等十分钟后再试!");
		} else if (errorClassName != null) {
			request.setAttribute("error", "未知错误：" + errorClassName);
		}
		
		if (errorClassName != null) {
			//登录失败，启用验证码
			session.setAttribute(ValidationCodeFilter.VALIDATION_CODE_ENABLED, true);
		} else {
			//进入登录页面，禁用验证码
			session.removeAttribute(ValidationCodeFilter.VALIDATION_CODE_ENABLED);
		}
		return destPage;
	}

	@RequestMapping("casLoginFail")
	public String loginFail() {
		String destPage = "system/sys/casLoginFail";

		return destPage;
	}

	@RequestMapping("unauthorized")
	public String unauthorized(HttpServletRequest request, HttpServletResponse response) {
		String destPage = "error/unauthorized";
		return destPage;
	}

	@RequestMapping("valImage")
	public void valImage(HttpSession session, HttpServletResponse response) throws IOException {
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 将图像填充随机背景色（制定范围）
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Times New Roman", Font.PLAIN, fontHeight);

		// 设置字体。
		g.setFont(font);
		// 画边框
		// g.drawRect(0, 0, width - 1, height - 1);
		// 随机产生155条干扰线，使图像中的认证码不易被其他程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);

			g.drawLine(x, y, x + xl, y + yl);
		}
		// randomCode用于保存随机产生的验证码， 以便用户登录后进行验证
		StringBuffer randomCode = new StringBuffer();
		int red = 20, green = 20, blue = 20;
		// 随机产生codecount数字的验证码
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字
			String strRand = String.valueOf(codeSequence[random.nextInt(36)]);
			// 产生随机的颜色分量来构造颜色值，这样输出的美味数字的颜色值都将不同。
			// 用随机产生的颜色将验证码绘制到图像中。
			g.setColor(new Color(red + random.nextInt(110), green + random.nextInt(110), blue + random.nextInt(110)));
			g.drawString(strRand, 13 * i + 6, 16);
			// 将产生的四个随机数组合在一起
			randomCode.append(strRand);
		}
		session.setAttribute(ValidationCodeFilter.VALIDATION_CODE_NAME, randomCode.toString());

		// 禁止图像缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expries", 0);
		response.setContentType("image/jpeg");

		// 清空缓存
		g.dispose();
		// 将图像输出到Servlet输出流中
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
	}

	private Color getRandColor(int fc, int bc) {

		int interval = fc;
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}

		Random random = new Random();
		int r = fc + random.nextInt(bc - interval);
		int g = fc + random.nextInt(bc - interval);
		int b = fc + random.nextInt(bc - interval);

		return new Color(r, g, b);
	}

}
