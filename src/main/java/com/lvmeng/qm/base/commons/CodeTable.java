package com.lvmeng.qm.base.commons;

import java.util.HashMap;
import java.util.Map;

import com.lvmeng.qm.base.vo.Pattern;
import com.lvmeng.qm.base.vo.Pattern_Type;

public class CodeTable {
	public static final String regex = "-*-";
	//key 问卷导入时所在列的索引
	public static final Map<Integer, Pattern> proPattern = new HashMap<>();
	public static final Map<Integer, Pattern> salePattern = new HashMap<>();
	public static final Map<Integer, Pattern> funcPattern = new HashMap<>();
	public static final Map<Integer, Pattern> contactPattern = new HashMap<>();
	static {
		proPattern.put(43, new Pattern(0, "【绿盟】的【技术】"+regex+"【伙伴】的【技术】"));
		
		proPattern.put(44, new Pattern(1, "硬件产品类项目"+regex+"安全服务类项目"));
		
		proPattern.put(148, new Pattern(2, "C4_1 A、服务态度需要改善 您认为我们在服务方面需要改进的是?"));
		proPattern.put(149, new Pattern(2, "C4_2 B、解决问题能力有限 您认为我们在服务方面需要改进的是?"));
		proPattern.put(150, new Pattern(2, "C4_3 C、响应及时性待加强 您认为我们在服务方面需要改进的是?"));
		
		proPattern.put(136, new Pattern(3, "C3_1 A、解决方案能力不足 您认为我们在专业能力方面需要改进的是?"));
		proPattern.put(137, new Pattern(3, "C3_2 B、实施的产品了解程度不够 您认为我们在专业能力方面需要改进的是?"));
		proPattern.put(138, new Pattern(3, "C3_3 C、专业技能不足 您认为我们在专业能力方面需要改进的是?"));
		proPattern.put(142, new Pattern(3, "CF3_1 A、解决方案能力不足 您认为在专业能力方面我们需要改进的是?"));
		proPattern.put(143, new Pattern(3, "CF3_2 B、交付验收文档不够清晰 您认为在专业能力方面我们需要改进的是?"));
		proPattern.put(144, new Pattern(3, "CF3_3 C、专业技能不足 您认为在专业能力方面我们需要改进的是?"));
		
		proPattern.put(156, new Pattern(4, "C、不如同类厂商表现好"));
		
		proPattern.put(158, new Pattern(5, "D、不会推荐"));
		
		proPattern.put(130, new Pattern(6, 6)); //C1 您对整个项目实施过程的满意程度评价是?
		
		proPattern.put(132, new Pattern(7, 6));	//C2 您给绿盟科技技术人员的表现打几分?
		
		proPattern.put(134, new Pattern(8, 6));	//CF1 您对项目整体服务过程的满意程度评价是?
		
		proPattern.put(112, new Pattern(9, Pattern_Type.ALL));	//B1.1_1 (文本框题结果) 请您简单描述不如其他厂商表现好的原因,我们可以怎么做?
		proPattern.put(114, new Pattern(9, Pattern_Type.ALL));	//B2.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		proPattern.put(116, new Pattern(9, Pattern_Type.ALL));	//B2.2.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		proPattern.put(118, new Pattern(9, Pattern_Type.ALL));	//B2.2.2.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		proPattern.put(125, new Pattern(9, Pattern_Type.ALL));	//B3.1_1 (文本框题结果) 选择“其他”,那么您认为我们在服务方面需要改进的是?
		proPattern.put(131, new Pattern(9, Pattern_Type.ALL));	//C1.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		proPattern.put(133, new Pattern(9, Pattern_Type.ALL));	//C2.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		proPattern.put(135, new Pattern(9, Pattern_Type.ALL));	//CF1.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		proPattern.put(141, new Pattern(9, Pattern_Type.ALL));	//C3.1_1 (文本框题结果) 选择“其他”,那么您认为我们在专业能力方面需要改进的是?
		proPattern.put(147, new Pattern(9, Pattern_Type.ALL));	//CF3.1_1 (文本框题结果) 选择“其他”,那么您认为我们在专业能力方面需要改进的是?
		proPattern.put(153, new Pattern(9, Pattern_Type.ALL));	//C4.1_1 (文本框题结果) 选择“其他”,那么您认为我们在专业能力方面需要改进的是?
		proPattern.put(155, new Pattern(9, Pattern_Type.ALL));	//A1.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		proPattern.put(157, new Pattern(9, Pattern_Type.ALL));	//A2.1_1 (文本框题结果) 请您简单描述不如其他厂商表现好的原因,我们可以怎么做?
		proPattern.put(178, new Pattern(9, Pattern_Type.ALL));	//Q86_1 (文本框题结果) 您还有什么意见建议吗?您的建议会使我们不断完善,以便为您提供更好的支撑。
		proPattern.put(109, new Pattern(9, Pattern_Type.ALL));	//D_1 (文本框题结果) 选择“其他”,那么您认为我们在产品方面需要改进的是?
		
		
		
		salePattern.put(43, new Pattern(0, "【绿盟】的【销售】"+regex+"【伙伴】的【销售】"));
		
		salePattern.put(111, new Pattern(1, "C、不如同类厂商表现好"));
		
		salePattern.put(156, new Pattern(2, "C、不如同类厂商表现好"));
		
		salePattern.put(158, new Pattern(3, "D、不会推荐"));
		
		salePattern.put(115, new Pattern(4, 6));	//B2.2 您给合作伙伴销售人员的表现打几分?
		
		salePattern.put(117, new Pattern(5, 6));	//B2.2.2 您给合作伙伴技术人员的表现打几分?
		
		salePattern.put(119, new Pattern(6, "B3_1 A、服务态度需要改善 您认为绿盟销售在服务方面需要改进的是?"));
		salePattern.put(120, new Pattern(6, "B3_2 B、解决问题能力有限 您认为绿盟销售在服务方面需要改进的是?"));
		salePattern.put(121, new Pattern(6, "B3_3 C、响应及时性待加强 您认为绿盟销售在服务方面需要改进的是?"));
		salePattern.put(122, new Pattern(6, "B3_4 D、缺乏日常沟通和交流 您认为绿盟销售在服务方面需要改进的是?"));
		
		salePattern.put(126, new Pattern(7, "A、希望销售主动联系进行介绍"));
		
		salePattern.put(127, new Pattern(8, "A、检测防御类:防火墙、防病毒、防泄密、流程监控、DDOS、抗拒绝、流量分析、安全隔离等" + regex 
				+ "B、安全评估类:配置核查、漏洞扫描、WEB扫描、网站检测类" + regex 
				+ "C、安全监管类:安全审计、数据库审计、审计防护、安全管家类" + regex
				+ "D、安全实验室类:安全攻防竞技、云计算虚拟化类" + regex
				+ "E、安全平台类:大数据安全分析类,绿盟云服务类" + regex
				+ "F、安全类服务:技术支持、渗透测试、咨询服务、网站监测、扫描评估" + regex
				+ "G、需求暂不明确,需要销售根据现状提供适合的方案"));
		
		salePattern.put(128, new Pattern(9, "6个月以上" + regex + "3-6个月内" + regex + "2-3个月内" + regex + "1个月之内"));
		
		salePattern.put(129, new Pattern(10, "F、对绿盟服务不满意"));
		
		salePattern.put(112, new Pattern(11, Pattern_Type.ALL));	//B1.1_1 (文本框题结果) 请您简单描述不如其他厂商表现好的原因,我们可以怎么做?
		salePattern.put(114, new Pattern(11, Pattern_Type.ALL));	//B2.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		salePattern.put(116, new Pattern(11, Pattern_Type.ALL));	//B2.2.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		salePattern.put(118, new Pattern(11, Pattern_Type.ALL));	//B2.2.2.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		salePattern.put(125, new Pattern(11, Pattern_Type.ALL));	//B3.1_1 (文本框题结果) 选择“其他”,那么您认为我们在服务方面需要改进的是?
		salePattern.put(131, new Pattern(11, Pattern_Type.ALL));	//C1.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		salePattern.put(133, new Pattern(11, Pattern_Type.ALL));	//C2.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		salePattern.put(135, new Pattern(11, Pattern_Type.ALL));	//CF1.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		salePattern.put(141, new Pattern(11, Pattern_Type.ALL));	//C3.1_1 (文本框题结果) 选择“其他”,那么您认为我们在专业能力方面需要改进的是?
		salePattern.put(147, new Pattern(11, Pattern_Type.ALL));	//CF3.1_1 (文本框题结果) 选择“其他”,那么您认为我们在专业能力方面需要改进的是?
		salePattern.put(153, new Pattern(11, Pattern_Type.ALL));	//C4.1_1 (文本框题结果) 选择“其他”,那么您认为我们在专业能力方面需要改进的是?
		salePattern.put(155, new Pattern(11, Pattern_Type.ALL));	//A1.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		salePattern.put(157, new Pattern(11, Pattern_Type.ALL));	//A2.1_1 (文本框题结果) 请您简单描述不如其他厂商表现好的原因,我们可以怎么做?
		salePattern.put(178, new Pattern(11, Pattern_Type.ALL));	//Q86_1 (文本框题结果) 您还有什么意见建议吗?您的建议会使我们不断完善,以便为您提供更好的支撑。
		salePattern.put(109, new Pattern(11, Pattern_Type.ALL));	//D_1 (文本框题结果) 选择“其他”,那么您认为我们在产品方面需要改进的是?
		
		
		
		funcPattern.put(110, new Pattern(0, Pattern_Type.ALL));	//D3_1 (文本框题结果) 如果您有产品缺陷和功能需求方面的建议,请您及时反馈给我们。需求被研发团队接纳,均可获赠100元京东礼品卡一张,您提供的问题需要满足以下其中之一:1、需求待规划中2、需求已被采纳

		funcPattern.put(112, new Pattern(1, Pattern_Type.ALL));	//B1.1_1 (文本框题结果) 请您简单描述不如其他厂商表现好的原因,我们可以怎么做?
		funcPattern.put(114, new Pattern(1, Pattern_Type.ALL));	//B2.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		funcPattern.put(116, new Pattern(1, Pattern_Type.ALL));	//B2.2.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		funcPattern.put(118, new Pattern(1, Pattern_Type.ALL));	//B2.2.2.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		funcPattern.put(125, new Pattern(1, Pattern_Type.ALL));	//B3.1_1 (文本框题结果) 选择“其他”,那么您认为我们在服务方面需要改进的是?
		funcPattern.put(131, new Pattern(1, Pattern_Type.ALL));	//C1.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		funcPattern.put(133, new Pattern(1, Pattern_Type.ALL));	//C2.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		funcPattern.put(135, new Pattern(1, Pattern_Type.ALL));	//CF1.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		funcPattern.put(141, new Pattern(1, Pattern_Type.ALL));	//C3.1_1 (文本框题结果) 选择“其他”,那么您认为我们在专业能力方面需要改进的是?
		funcPattern.put(147, new Pattern(1, Pattern_Type.ALL));	//CF3.1_1 (文本框题结果) 选择“其他”,那么您认为我们在专业能力方面需要改进的是?
		funcPattern.put(153, new Pattern(1, Pattern_Type.ALL));	//C4.1_1 (文本框题结果) 选择“其他”,那么您认为我们在专业能力方面需要改进的是?
		funcPattern.put(155, new Pattern(1, Pattern_Type.ALL));	//A1.1_1 (文本框题结果) 请您简单描述以上给出低分的原因,我们可以怎么改善?
		funcPattern.put(157, new Pattern(1, Pattern_Type.ALL));	//A2.1_1 (文本框题结果) 请您简单描述不如其他厂商表现好的原因,我们可以怎么做?
		funcPattern.put(178, new Pattern(1, Pattern_Type.ALL));	//Q86_1 (文本框题结果) 您还有什么意见建议吗?您的建议会使我们不断完善,以便为您提供更好的支撑。
		funcPattern.put(109, new Pattern(1, Pattern_Type.ALL));	//D_1 (文本框题结果) 选择“其他”,那么您认为我们在产品方面需要改进的是?
		
		contactPattern.put(42, new Pattern(0, Pattern_Type.ALL)); //Q1 与绿盟科技的合作过程中您的主要角色是属于?
		contactPattern.put(172, new Pattern(1, Pattern_Type.COMPARE)); //Q85_1 姓名 为了方便为您提供绿盟科技的安全事件、安全动态、安全资讯等信息,请您完善以下联系方式,我们将定期为您发送。感谢您的支持!
		contactPattern.put(173, new Pattern(2, Pattern_Type.ALL)); //Q85_2 单位 为了方便为您提供绿盟科技的安全事件、安全动态、安全资讯等信息,请您完善以下联系方式,我们将定期为您发送。感谢您的支持!
		contactPattern.put(174, new Pattern(3, Pattern_Type.COMPARE)); //Q85_3 邮箱 为了方便为您提供绿盟科技的安全事件、安全动态、安全资讯等信息,请您完善以下联系方式,我们将定期为您发送。感谢您的支持!
		contactPattern.put(175, new Pattern(4, Pattern_Type.COMPARE)); //Q85_4 电话 为了方便为您提供绿盟科技的安全事件、安全动态、安全资讯等信息,请您完善以下联系方式,我们将定期为您发送。感谢您的支持!
		contactPattern.put(176, new Pattern(5, Pattern_Type.COMPARE)); //Q85_5 地址 为了方便为您提供绿盟科技的安全事件、安全动态、安全资讯等信息,请您完善以下联系方式,我们将定期为您发送。感谢您的支持!
	}
}