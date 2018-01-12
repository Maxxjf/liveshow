package com.qcloud.liveshow.enums;


/**
 * 请求数据枚举
 * 
 * @author mrliu
 *
 */
public class RequestDataEnum {

	/**
	 * 请求数据动作
	 * @author mrliu
	 *
	 */
	public enum ActionType {

		/**
		 * 0 AUTH 鉴权
		 */
		AUTH(0, "鉴权"),

		/**
		 * 1 GROUP_CHAT 群聊
		 */
		GROUP_CHAT(1, "群聊"),
		/**
		 * 2 PRIVATE_CHAT 私聊
		 */
		PRIVATE_CHAT(2, "私聊"),
		/**
		 * 3 PRIVATE_CHAT 送礼物
		 */
		GIVING_GIFTS(3, "送礼物"),
		/**
		 * 4 PULL_THE_BLACK 送礼物
		 */
		PULL_THE_BLACK(4, "拉黑"),
		/**
		 * 5 NO_SPEAKING 禁止发言
		 *//*
		NO_SPEAKING(5, "禁止发言"),
		*//**
		 * 6 UN_NO_SPEAKING 送礼物
		 *//*
		UN_NO_SPEAKING(6, "解除禁止发言"),*/
		/**
		 * 7 KICK_OUT_ROOM 送礼物
		 */
		KICK_OUT_ROOM(7, "踢出房间"),
		/**
		 * 8 GUARD 设置守护
		 */
		GUARD(8, "设置守护"),
		/**
		 * 10 IN_ROOM 进入房间
		 */
		IN_ROOM(10, "进入房间"),
		/**
		 * 11 OUT_ROOM 退出房间
		 */
		OUT_ROOM(11, "退出房间"),
		/**
		 * 12 ROOM_NOTICE 房间公告
		 */
		ROOM_NOTICE(12, "房间公告"),
		/**
		 * 13 ROOM_FORBIDDEN_CHAT 禁言/取消禁言
		 */
		ROOM_FORBIDDEN_CHAT(13, "禁言/取消禁言"),
		/**
		 * 14 DELETE_MESSAGE_CHAT 删除私聊列表
		 */
		DELETE_MESSAGE_CHAT(14, "删除私聊列表"),
		/**
		 * 14 DELETE_MESSAGE_CHAT 删除私聊列表
		 */
		PAY_VIP_ROOM(17, "收费直播的计算"),
		/**
		 * 100 NEW_SERVER_ONLINE 新的TCP服务器上线
		 */
		NEW_SERVER_ONLINE(100, "新的TCP服务器上线"),
		/**
		 * 101 MESSAGE_FORWARDING 消息转发
		 */
		MESSAGE_FORWARDING(101, "消息转发"),
		/**
		 * 102 GET_NOT_READ_MESSAGE 获取未发送私聊信息
		 */
		GET_NOT_READ_MESSAGE(102, "获取未发送私聊信息"),
		/**
		 * 103 CLOSE_LIVE_BROADCAST 获取未发送私聊信息
		 */
		CLOSE_LIVE_BROADCAST(103, "关闭直播"),
		/**
		 * 104 GET_PRIVATE_CHAT_LIST 获取未发送私聊信息
		 */
		GET_PRIVATE_CHAT_LIST(104, "获取私聊联系人列表"),
		
		/**
		 * 105 UPDATE_PRIVATE_CHAT_ISREAD 修改未读私信为已读
		 */
		UPDATE_PRIVATE_CHAT_ISREAD(105, "修改未读私信为已读");

		private final Integer key;
		private final String name;

		private ActionType(int key, String name) {
			this.key = key;
			this.name = name;
		}

		public Integer getKey() {
			return key;
		}
		
		public String getName() {
			return name;
		}

		public static ActionType get(int key) {
			for (ActionType e : ActionType.values()) {
				if (e.getKey() == key) {
					return e;
				}
			}
			return AUTH;
		}
		
		

	}
}
