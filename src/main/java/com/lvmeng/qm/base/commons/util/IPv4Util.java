package com.lvmeng.qm.base.commons.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class IPv4Util {

	public static void main(String[] args) {
		String[] s=searchIp("124.193.222.226");
		System.out.println(s);
		// ipBuilder();

	}

	
	private static Map<String, String[]> ipMap = null;
	private static List<Long> keyList = new ArrayList<Long>();

	public static String[] searchIp(String ip) {
		if (keyList == null || ipMap == null) {
			getIpMap();
		}
		String[] r = null;
		long i = ipToLong(ip);
		int index = binarySearch(keyList, 0, keyList.size()-1, i);
		if (index < 0) {
			return null;
		}
		long keyIp = keyList.get(index);
		if (keyIp > 0) {
			r = ipMap.get(String.valueOf(keyIp));
			long endIp = Long.parseLong(r[1]);
			if(i>endIp){
				return null;
			}
		}
		return r;
	}

	private static Map<String, String[]> getIpMap() {
		if (ipMap == null || ipMap.size() == 0) {
			ipMap = new HashMap<String, String[]>();
			// BufferedReader:从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。
			BufferedReader bufReader = null;
			try {
				String path = URLDecoder.decode(IPv4Util.class.getResource("/").getPath() + "/IP.txt", "utf-8");
//				path = "d:/ip_builder/ipall.txt";
				File file = new File(path);
				// FileReader:用来读取字符文件的便捷类。
				bufReader = new BufferedReader(new FileReader(file));
				String temp = null;
				while ((temp = bufReader.readLine()) != null) {
					// System.out.println(temp);
					String[] ip_array = temp.split(":");
					String key = ip_array[0];
					ipMap.put(key, ip_array);
					keyList.add(Long.parseLong(ip_array[0]));
				}
			} catch (Exception e) {
				e.getStackTrace();
			} finally {
				if (bufReader != null) {
					try {
						bufReader.close();
					} catch (IOException e) {
						e.getStackTrace();
					}
				}
			}
		}
		return ipMap;
	}

	public static int binarySearch(List<Long> array, int start, int end, long value) {
		if (end < start) {
			return -1;
		}
		int middle = (start + end) / 2;
//		System.out.println(start + "___" + middle + "___" + end);
//		System.out.println(array.get(start) + "___" +  array.get(middle) + "___" +  array.get(end));
		if (end == (start + 1)) {

			if (array.get(start) <= value && array.get(end) > value) {
				return start;
			} else if (array.get(end) == value) {
				return end;
			} else {
				return -1;
			}

		} else if (end == (start + 2)) {
			if (array.get(start) <= value && array.get(middle) > value) {
				return start;
			} else if (array.get(middle) < value && array.get(end) > value) {
				return middle;
			} else if (array.get(end) == value) {
				return end;
			} else {
				return -1;
			}

		} else if (array.get(middle) == value) {
			return middle;
		} else if (value > array.get(middle)) {
			return binarySearch(array, middle + 1, end, value);
		} else if (value < array.get(middle)) {
			return binarySearch(array, start, middle - 1, value);
		}
		return -1;

	}

	public static long ipToLong(String ipAddress) {

		long result = 0;

		String[] ipAddressInArray = ipAddress.split("\\.");

		for (int i = 3; i >= 0; i--) {

			long ip = Long.parseLong(ipAddressInArray[3 - i]);

			// left shifting 24,16,8,0 and bitwise OR

			// 1. 192 << 24
			// 1. 168 << 16
			// 1. 1 << 8
			// 1. 2 << 0
			result |= ip << (i * 8);
		}

		return result;
	}

	public static String longToIp(long i) {

		return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);

	}

	/**
	 * 获取客户端IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-Forwarded-For");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
			// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * 获取客户端IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteHost(HttpServletRequest request) {
		String host = request.getHeader("Host");
		if (StringUtils.isBlank(host)){
			StringBuffer url = request.getRequestURL();
			host = url.delete(url.length() - request.getRequestURI().length(), url.length()).delete(0, 7).toString();
		}
		return host;
	}
	
	public static String[] provinceName = { "中国", "安徽", "北京", "重庆", "福建", "广东", "甘肃", "广西", "贵州", "河南", "湖北", "河北", "海南", "香港", "黑龙江", "湖南", "吉林",
			"江苏", "江西", "辽宁", "澳门", "内蒙古", "宁夏", "青海", "四川", "山东", "上海", "山西", "天津", "台湾", "陕西", "新疆", "西藏", "云南", "浙江" };
	public static String[] provinceCode = { "189", "501", "502", "503", "504", "505", "506", "507", "508", "509", "510", "511", "512", "513", "514",
			"515", "516", "517", "518", "519", "520", "521", "522", "523", "524", "525", "526", "527", "528", "529", "530", "531", "532", "533",
			"534" };

	public static String[] cityName = { "合肥市", "福州市", "广州市", "深圳市", "兰州市", "南宁市", "贵阳市", "郑州市", "武汉市", "保定市", "秦皇岛市", "石家庄市", "唐山市", "海口市", "哈尔滨市",
			"长沙市", "长春市", "吉林市", "南京市", "苏州市", "无锡市", "南昌市", "大连市", "沈阳市", "呼和浩特", "银川市", "西宁市", "成都市", "济南市", "青岛市", "太原市", "西安市", "乌鲁木齐市", "拉萨市",
			"昆明市", "杭州市", "宁波市", "台北市" };
	public static String[] cityCode = { "708", "1301", "1504", "1515", "1709", "1908", "2104", "2318", "2511", "2701", "2707", "2708", "2709",
			"2902", "3303", "3502", "3703", "3704", "3904", "3907", "3909", "4106", "4305", "4312", "4705", "4902", "5101", "5303", "5505", "5510",
			"5909", "6506", "6708", "6901", "7106", "7302", "7306", "10000" };

	public static void ipBuilder() {

		BufferedReader bufReader = null;
		BufferedWriter bufWriter = null;
		List<String[]> list = new ArrayList<String[]>();
		try {
			String path = URLDecoder.decode("d:/ip_builder/ip_original.txt", "utf-8");

			File file = new File(path);
			// FileReader:用来读取字符文件的便捷类。
			bufReader = new BufferedReader(new FileReader(file));
			String temp = null;
			String[] pre = { "0", "0", "0", "0", "0","0" };
			while ((temp = bufReader.readLine()) != null) {
				if (isAbroad(temp)) {
					continue;
				}
				String s[] = parse(provinceName, provinceCode, cityName, cityCode, temp);
				if (s != null) {
					if (pre[4].equals(s[4])) {
						// System.out.println(temp);
						long endIp = Long.parseLong(pre[1]);
						long startIp2 = Long.parseLong(s[0]);
						if (endIp + 1 == startIp2) {
							pre[1] = s[1];
						}
					} else {
						list.add(pre);
						pre = s;
					}
				} else {
					// System.out.println(temp);
				}
			}
			list.add(pre);

			bufWriter = new BufferedWriter(new FileWriter("d:/ip_builder/ipall.txt"));
			for (String[] key : list) {

				bufWriter.write(key[0] + ":" + key[1] + ":" + key[2] + ":" + key[3]+ ":" + key[4]);
				bufWriter.newLine();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufReader != null) {
				try {
					bufReader.close();
				} catch (IOException e) {
					e.getStackTrace();
				}
			}
			if (bufWriter != null) {
				try {
					bufWriter.close();
				} catch (IOException e) {
					e.getStackTrace();
				}
			}

		}

	}

	private static String[] parse(String[] provinceName, String[] provinceCode, String[] cityName, String[] cityCode, String ip_address) {

		String[] result = null;// 0 s, 1 e ,2 adressCode ,3 location,4
		// key
		String start_ip = "", end_ip = "", addr = "", memo = "";
		start_ip = ip_address.substring(0, 15).trim();
		end_ip = ip_address.substring(16, 31).trim();

		String a = ip_address.substring(32).trim();
		int index = a.indexOf(" ");
		if (index == -1) {
			addr = a;
		} else {
			addr = a.substring(0, index).trim();
			memo = a.substring(index + 1).trim();
		}

		for (int i = 0; i < provinceName.length; i++) {
			if (addr.contains(provinceName[i])) {
				result = new String[6];
				result[0] = String.valueOf(ipToLong(start_ip));
				result[1] = String.valueOf(ipToLong(end_ip));
				String city = "-";
				for (int j = 0; j < cityName.length; j++) {
					if (addr.contains(cityName[j])) {
						city = cityCode[j];
						break;
					}
				}
				result[2] = provinceCode[i];
				result[3] = city;
				result[4] = netLocation(addr, memo);
				result[5] = result[2] + "," + result[3]+ "," + result[4];
				break;
			}
		}
		if (result == null) {
			result = special(ip_address, start_ip, end_ip);
		}

		return result;
	}

	private static String[] special(String ip_address, String start_ip, String end_ip) {

		String[] result = null;
		String newAdress = "";
		if (ip_address.contains("华中") || ip_address.contains("武汉") || ip_address.contains("长江大学") || ip_address.contains("中南财经")) {
			newAdress = "湖北省武汉市 ";
		} else if (ip_address.contains("中南大学")) {
			newAdress = "湖南 长沙 ";
		} else if (ip_address.contains("宁波大学")) {
			newAdress = "浙江宁波 ";
		} else if (ip_address.contains("成都")) {
			newAdress = "四川成都 ";
		} else if (ip_address.contains("哈尔滨")) {
			newAdress = "黑龙江哈尔滨 ";
		} else if (ip_address.contains("佳木斯")) {
			newAdress = "黑龙江佳木斯 ";
		} else if (ip_address.contains("大庆")) {
			newAdress = "黑龙江 大庆";
		} else if (ip_address.contains("齐齐哈尔")) {
			newAdress = "黑龙江 齐齐哈尔";
		} else if (ip_address.contains("太原") || ip_address.contains("中北")) {
			newAdress = "山西太原 ";
		} else if (ip_address.contains("西安") || ip_address.contains("西北")) {
			newAdress = "陕西西安";
		} else if (ip_address.contains("郑州") || ip_address.contains("黄河科技大学")) {
			newAdress = "河南省郑州市 ";
		} else if (ip_address.contains("青岛")) {
			newAdress = "山东青岛 ";
		} else if (ip_address.contains("东北大学")) {
			newAdress = "辽宁沈阳 ";
		} else if (ip_address.contains("华东") || ip_address.contains("东华大学")) {
			newAdress = "上海 ";
		} else if (ip_address.contains("南京") || ip_address.contains("东南大学")) {
			newAdress = "江苏南京 ";
		} else if (ip_address.contains("合肥")) {
			newAdress = "安徽合肥 ";
		} else if (ip_address.contains("大连")) {
			newAdress = "辽宁大连 ";
		} else if (ip_address.contains("东北农业大学")) {
			newAdress = "黑龙江省哈尔滨市 ";
		} else if (ip_address.contains("广州") || ip_address.contains("华南") || ip_address.contains("中山大学")) {
			newAdress = "广东 广州 ";
		} else if (ip_address.contains("南开大学")) {
			newAdress = "天津 ";
		} else if (ip_address.contains("青海")) {
			newAdress = "青海省西宁市 ";
		} else if (ip_address.contains("西华大学")) {
			newAdress = "四川 成都 ";
		} else if (ip_address.contains("长沙")) {
			newAdress = "湖南长沙 ";
		} else if (ip_address.contains("南昌")) {
			newAdress = "江西南昌";
		} else if (ip_address.contains("对外经济贸易") || ip_address.contains("首都") || ip_address.contains("北方工业大学") || ip_address.contains("中央")
				|| ip_address.contains("清华")) {
			newAdress = "北京";
		} else if (ip_address.contains("华北科技学院")) {
			newAdress = "河北省廊坊市";
		} else if (ip_address.contains("长春")) {
			newAdress = "吉林 长春";
		} else if (ip_address.contains("集美大学")) {
			newAdress = "福建厦门";
		} else if (ip_address.contains("兰州")) {
			newAdress = "甘肃 兰州";
		} else if (ip_address.contains("东北三省") || ip_address.contains("聚友网络")) {
			newAdress = "中国";
		}

		for (int i = 0; i < provinceName.length; i++) {
			if (newAdress.contains(provinceName[i])) {
				result = new String[5];
				result[0] = String.valueOf(ipToLong(start_ip));
				result[1] = String.valueOf(ipToLong(end_ip));
				String city = "0";
				for (int j = 0; j < cityName.length; j++) {
					if (newAdress.contains(cityName[j])) {
						city = cityCode[j];
						break;
					}
				}
				result[2] = provinceCode[i] + "," + city;
				result[3] = netLocation(newAdress, ip_address);
				result[4] = result[2] + "," + result[3];
				break;
			}
		}

		return result;
	}

	// 上网场景
	private static String netLocation(String addr, String memo) {

		// CodeMap.put("170", "公共场所");
		// CodeMap.put("171", "公司");
		// CodeMap.put("172", "家");
		// CodeMap.put("173", "学校");
		// CodeMap.put("174", "其它");

		if (memo == null || memo.contains("CZ88.NET") || memo.contains("数据中心") || memo.contains("信息中心")) {
			return "174";
		}
		if (memo.contains("商城") || memo.contains("商场") || memo.contains("俱乐部") || memo.contains("宾馆") || memo.contains("娱乐城") || memo.contains("洗浴")
				|| memo.contains("公园") || memo.contains("广场") || memo.contains("餐厅") || memo.contains("影楼") || memo.contains("专卖")
				|| memo.contains("文化宫") || memo.contains("会所") || memo.contains("馆") || memo.contains("乐园") || memo.contains("图书馆")
				|| memo.contains("市场") || memo.contains("景区") || memo.contains("阅览室") || memo.contains("超市") || memo.contains("影院")
				|| memo.contains("店") || memo.contains("酒吧") || memo.contains("休闲")) {
			return "170";
		}
		if (memo.contains("购物中心") || memo.contains("商业中心") || memo.contains("文化中心") || memo.contains("娱乐中心") || memo.contains("会员中心")
				|| memo.contains("交流中心") || memo.contains("竞技中心") || memo.contains("商务中心") || memo.contains("体验中心") || memo.contains("上网中心")) {
			return "170";
		}

		if (memo.contains("网吧") || memo.contains("网苑") || memo.contains("网上家园") || memo.contains("网都") || memo.contains("上网室")) {
			return "170";
		}
		if ((memo.contains("网") || memo.contains("网络") || memo.contains("网城") || memo.contains("E网") || memo.contains("网艺") || memo.contains("网盟")
				|| memo.contains("网咖") || memo.contains("网点") || memo.contains("网馆") || memo.contains("网族") || memo.contains("网事")
				|| memo.contains("网室") || memo.contains("网易") || memo.contains("网民"))
				&& (!memo.contains("公司"))) {
			return "170";
		}
		if (memo.contains("银行") || memo.contains("合作社") || memo.contains("信用社")) {
			return "175";
		}
		if (memo.contains("医院") || memo.contains("卫生所") || memo.contains("卫生院") || memo.contains("保健院")) {
			return "176";
		}

		// -----------------------------------------
		if (memo.contains("公司") || memo.contains("集团") || memo.contains("大厦") || memo.contains("创业") || memo.contains("园区") || memo.contains("研究院")
				|| memo.contains("科学城") || memo.contains("研究所") || memo.contains("软件园") || memo.contains("设计院") || memo.contains("开发区")
				|| memo.contains("证券")) {
			return "171";
		}
		if (memo.contains("工厂") || memo.contains("工业") || memo.contains("基地") || memo.contains("电脑") || memo.contains("中心") || memo.contains("办公室")) {

			return "171";
		}
		if (memo.endsWith("厂") || memo.contains("局") || memo.contains("部") || memo.contains("所") || memo.contains("处") || memo.contains("厅")
				|| memo.contains("院")) {

			return "171";
		}
		// -----------------------------------------
		if (addr.contains("学") || addr.contains("师") || memo.contains("教育网") || memo.contains("大学") || memo.contains("中学") || memo.contains("小学")
				|| memo.contains("初中") || memo.contains("高中") || memo.contains("学院") || memo.contains("学校") || memo.contains("分校")
				|| memo.contains("学生") || memo.contains("理工") || memo.contains("教学楼") || memo.contains("师大") || memo.contains("专科")
				|| memo.contains("师专") || memo.contains("公学") || memo.contains("技校") || memo.contains("一高") || memo.contains("高中")
				|| memo.contains("中专") || memo.contains("职专") || memo.contains("网校") || memo.contains("电大") || memo.contains("附中")
				|| memo.contains("附小") || memo.contains("一中") || memo.contains("交大") || memo.contains("科大") || memo.contains("煤校")
				|| memo.contains("工校") || memo.contains("职校") || memo.contains("二中") || memo.contains("三中") || memo.contains("四中")
				|| memo.contains("艺校") || memo.contains("一小") || memo.contains("二小") || memo.contains("三小") || memo.contains("四小")
				|| memo.contains("校园网") || memo.contains("卫校") || memo.contains("党校") || memo.contains("幼儿园")) {

			return "173";
		}

		if (memo.contains("电信") || memo.contains("联通") || memo.contains("移动") || memo.contains("铁通") || memo.contains("歌华有线") || memo.contains("教育网")
				|| memo.contains("宽带") || memo.contains("宽频") || memo.contains("有线通") || memo.contains("小区") || memo.contains("宿舍")) {
			return "172";
		}

		// System.out.println(addr + "______" + memo);
		return "174";
	}

	// ----------------------------------------------------------------------------

	public static boolean isAbroad(String temp) {
		return (temp.contains("IANA") || temp.contains("美国") || temp.contains("日本") || temp.contains("伊朗") || temp.contains("印度")
				|| temp.contains("澳大利亚") || temp.contains("印度") || temp.contains("欧洲") || temp.contains("英国") || temp.contains("荷兰")

				|| temp.contains("波兰") || temp.contains("阿尔巴尼亚") || temp.contains("乌兹别克斯坦") || temp.contains("德国") || temp.contains("俄罗斯")

				|| temp.contains("奥地利") || temp.contains("捷克") || temp.contains("意大利") || temp.contains("瑞典") || temp.contains("芬兰")

				|| temp.contains("保加利亚") || temp.contains("法国") || temp.contains("立陶宛") || temp.contains("乌克兰") || temp.contains("沙特阿拉伯")

				|| temp.contains("韩国") || temp.contains("孟加拉") || temp.contains("菲律宾") || temp.contains("新喀里多尼亚") || temp.contains("越南")

				|| temp.contains("新加坡") || temp.contains("马来西亚") || temp.contains("泰国") || temp.contains("瓦努阿图") || temp.contains("新西兰")

				|| temp.contains("斯里兰卡") || temp.contains("巴基斯坦") || temp.contains("柬埔寨") || temp.contains("萨摩亚") || temp.contains("拉脱维亚")

				|| temp.contains("挪威") || temp.contains("法属") || temp.contains("蒙古") || temp.contains("西班牙") || temp.contains("以色列")

				|| temp.contains("丹麦") || temp.contains("希腊") || temp.contains("马其顿") || temp.contains("匈牙利") || temp.contains("卢森堡")

				|| temp.contains("肯尼亚") || temp.contains("瑞士") || temp.contains("哈萨克斯坦") || temp.contains("土耳其") || temp.contains("比利时")

				|| temp.contains("塞尔维亚") || temp.contains("马里") || temp.contains("土库曼斯坦") || temp.contains("冰岛") || temp.contains("列支敦士登")

				|| temp.contains("巴西") || temp.contains("加拿") || temp.contains("墨西哥") || temp.contains("阿根廷") || temp.contains("巴拿马")

				|| temp.contains("厄瓜多尔") || temp.contains("乌拉圭") || temp.contains("哥伦比亚") || temp.contains("北美") || temp.contains("哥斯达黎加")

				|| temp.contains("智利") || temp.contains("秘鲁") || temp.contains("拉美") || temp.contains("古巴") || temp.contains("特立尼达和多巴哥")

				|| temp.contains("罗马尼亚") || temp.contains("马耳他") || temp.contains("吉尔吉斯斯坦") || temp.contains("尼日利亚") || temp.contains("斯洛文尼亚")
				|| temp.contains("摩纳哥") || temp.contains("爱沙尼亚") || temp.contains("尼亚") || temp.contains("摩尔多瓦") || temp.contains("科威特")
				|| temp.contains("阿联酋") || temp.contains("葡萄牙")
				|| temp.contains("喀麦隆")
				|| temp.contains("埃及")
				|| temp.contains("直布罗陀")

				|| temp.contains("阿尔及利亚")
				|| temp.contains("圣文森特和格林纳丁斯")
				|| temp.contains("安提瓜和巴布达")
				|| temp.contains("委内瑞拉")
				|| temp.contains("巴巴多斯")

				// ||temp.contains("")||temp.contains("")||temp.contains("")||temp.contains("")||temp.contains("")

				|| temp.contains("波多黎各") || temp.contains("南非") || temp.contains("突尼斯") || temp.contains("克罗地亚") || temp.contains("尼加拉瓜")

				|| temp.contains("斯洛伐克") || temp.contains("牙买加") || temp.contains("布基纳法索") || temp.contains("关岛") || temp.contains("危地马拉")

				|| temp.contains("波多黎各") || temp.contains("加蓬") || temp.contains("亚洲") || temp.contains("非洲") || temp.contains("摩洛哥")
				|| temp.contains("安哥拉") || temp.contains("纳米比亚") || temp.contains("马拉维") || temp.contains("科特迪瓦") || temp.contains("毛里求斯")

				|| temp.contains("莫桑比克") || temp.contains("赤道几内亚") || temp.contains("博茨瓦纳") || temp.contains("苏丹") || temp.contains("巴哈马")
				|| temp.contains("巴林") || temp.contains("塔吉克斯坦") || temp.contains("黑山") || temp.contains("斯坦") || temp.contains("也门")
				|| temp.contains("黎巴嫩") || temp.contains("约旦") || temp.contains("塞浦路斯") || temp.contains("安道尔") || temp.contains("马恩岛")

				|| temp.contains("欧盟") || temp.contains("伊拉克") || temp.contains("卡塔尔") || temp.contains("阿曼") || temp.contains("利比亚")
				|| temp.contains("格鲁吉亚") || temp.contains("叙利亚") || temp.contains("塞舌尔") || temp.contains("尼泊尔") || temp.contains("巴布亚新几内亚")
				|| temp.contains("马尔代夫") || temp.contains("阿富汗") || temp.contains("叙利亚") || temp.contains("阿塞拜疆") || temp.contains("泽西岛")
				|| temp.contains("赞比亚") || temp.contains("马达加斯加") || temp.contains("贝宁") || temp.contains("乍得") || temp.contains("加纳")

				|| temp.contains("亚太") || temp.contains("刚果") || temp.contains("卢旺达") || temp.contains("格陵兰") || temp.contains("群岛")

				|| temp.contains("CZ88") || temp.contains("百慕大") || temp.contains("瓜德罗普") || temp.contains("圣基茨") || temp.contains("老挝")

				|| temp.contains("马来") || temp.contains("印尼") || temp.contains("澳洲") || temp.contains("英格兰") || temp.contains("南韩")
				|| temp.contains("朝鲜") || temp.contains("荷属") || temp.contains("玻利维亚") || temp.contains("菲律") || temp.contains("不丹")
				|| temp.contains("斐济") || temp.contains("罗尼") || temp.contains("波黑") || temp.contains("文莱") || temp.contains("莱索托") || temp
				.contains("爱尔兰"));
	}

}