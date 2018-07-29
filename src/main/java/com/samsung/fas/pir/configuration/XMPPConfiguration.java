package com.samsung.fas.pir.configuration;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.stringprep.XmppStringprepException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.xmpp.config.XmppConnectionFactoryBean;

import javax.net.ssl.SSLSocketFactory;

//@Configuration
//@ImportResource("classpath:integration.xml")
public class XMPPConfiguration {
	@Bean
	public XmppConnectionFactoryBean xmppConnectionFactoryBean() {
		try {
			XMPPTCPConnectionConfiguration	configuration = XMPPTCPConnectionConfiguration.builder()
					.setXmppDomain("gcm.googleapis.com")
					.setHost("fcm-xmpp.googleapis.com").setPort(5236)
					.setUsernameAndPassword("701894160434@gcm.googleapis.com","AAAAo2wm8DI:APA91bHyDAJ8VNyU372kp-n35bA_-kyX7Zm3mIcVmvAvdGwrOaRZV3PIntvKS_ehUHb0fBRg0qDmNcnknncsZXDoZuTl9goSamnwbEBNLe17qDAm4P-n8LbrylhB2sCkiRQAwlYtmCu_")
					.setSecurityMode(ConnectionConfiguration.SecurityMode.ifpossible)
					.setSendPresence(false)
					.setSocketFactory(SSLSocketFactory.getDefault())
					.build();
			XmppConnectionFactoryBean			factoryBean 		= new XmppConnectionFactoryBean();
			factoryBean.setConnectionConfiguration(configuration);
			factoryBean.setSubscriptionMode(null);
			return factoryBean;
		} catch (XmppStringprepException e) {
			e.printStackTrace();
			return null;
		}
	}
}
