package com.qcloud.liveshow.enums;

/**
 * 返回数据枚举
 * @author mrliu
 *
 */
public class ResponseDataEnum {

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
		 */
		NO_SPEAKING(5, "禁止发言"),
		
		/**
		 * 6 UN_NO_SPEAKING 送礼物
		 */
		UN_NO_SPEAKING(6, "解除禁止发言"),
		
		/**
		 * 7 KICK_OUT_ROOM 送礼物
		 */
		KICK_OUT_ROOM(7, "踢出房间"),
		
		/**
		 * 8 GUARD 设置守护
		 */
		GUARD(8, "设置守护"),
		
		/**
		 * 9 UN_GUARD 解除守护
		 */
		UN_GUARD(9, "解除守护"),
		
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
		UPDATE_PRIVATE_CHAT_ISREAD(105, "修改未读私信为已读"),
		
		/**
		 * 203 USER_REMOVE_GROUP_CHAT 有用户从直播室群聊退出
		 */
		USER_REMOVE_GROUP_CHAT(203, "有用户从直播室群聊退出"),
		
		/**
		 * 204 USER_ADD_GROUP_CHAT 有用户加入直播室群聊
		 */
		USER_ADD_GROUP_CHAT(204, "有用户加入直播室群聊");
		

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
	
	/**
	 * 请求数据动作
	 * @author mrliu
	 *
	 */
	public enum Code {

		/**
		 * 0 SUCCESS 成功
		 */
		SUCCESS(0, "成功"),

		/**
		 * 1 FAIL 失败
		 */
		FAIL(1, "失败"),
		/**
		 * 2 AUTH_FAIL 鉴权失败
		 */
		AUTH_FAIL(2, "鉴权失败"),
		/**
		 * 3 DATA_ERRER 数据格式错误
		 */
		DATA_ERRER(3, "数据格式错误"),
		/**
		 * 4 PARAMETER_MISSING 缺少必要参数
		 */
		PARAMETER_MISSING(4, "缺少必要参数"),
		
		/**
		 * 5 FORBIDDEN_CHAT 已被禁言
		 */
		FORBIDDEN_CHAT(5, "已被禁言"),
		
		/**
		 * 6 BLACK_LIST  已被拉黑
		 */
		BLACK_LIST(6, "已被拉黑");

		private final Integer key;
		private final String name;

		private Code(int key, String name) {
			this.key = key;
			this.name = name;
		}

		public Integer getKey() {
			return key;
		}

		public String getName() {
			return name;
		}

		public static Code get(int key) {
			for (Code e : Code.values()) {
				if (e.getKey() == key) {
					return e;
				}
			}
			return SUCCESS;
		}

	}
}
