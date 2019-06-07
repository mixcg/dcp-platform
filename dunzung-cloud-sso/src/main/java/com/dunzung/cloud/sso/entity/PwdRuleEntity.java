package com.dunzung.cloud.sso.entity;

import lombok.Data;

@Data
public class PwdRuleEntity {

	private Integer minLength;

	//@Description("{title: '密码最大长度'}")
	private Integer maxLength;

	//@Description("{title: '是否包含大写字母'}")
	private Integer containsAZ;

	//@Description("{title: '是否包含小写字母'}")
	private Integer containsaz;

	//@Description("{title: '是否包含数字'}")
	private Integer contains09;

	//@Description("{title: '包含的特殊字符'}")
	private String containsSpecialChars;

	//@Description("{title: '是否允许包含用户名'}")
	private Integer containsUsername;

	//@Description("{title: '历史密码不允许重复的次数'}")
	private Integer notRepeatHisNumbs;

	//@Description("{title: '密码错误次数'}")
	private Integer errorNumbs;

	//@Description("{title: '首次登陆是否修改密码'}")
	private Integer resetAtFirstLogin;

	//@Description("{title: '密码修改间隔天数'}")
	private Integer lastTimeIn;

	//@Description("{title: '默认密码'}")
	private String defaultPwd;

	public String toString() {
		return "PasswordRules [密码最小长度：" + minLength + ", 密码最大长度：" + maxLength + ", 是否包含大写字母：" + containsAZ
				+ ", 是否包含小写字母：" + containsaz + ", 是否包含数字：" + contains09 + ", 是否包含特殊字符：" + containsSpecialChars
				+ ", 不允许包含用户名：" + containsUsername + ", 历史密码不允许重复的次数：" + notRepeatHisNumbs + ", 密码错误次数：" + errorNumbs
				+ ", 首次登陆是否修改密码：" + resetAtFirstLogin + ", 密码修改间隔时间：" + lastTimeIn + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contains09 == null) ? 0 : contains09.hashCode());
		result = prime * result + ((containsAZ == null) ? 0 : containsAZ.hashCode());
		result = prime * result + ((containsSpecialChars == null) ? 0 : containsSpecialChars.hashCode());
		result = prime * result + ((containsaz == null) ? 0 : containsaz.hashCode());
		result = prime * result + ((defaultPwd == null) ? 0 : defaultPwd.hashCode());
		result = prime * result + ((errorNumbs == null) ? 0 : errorNumbs.hashCode());
		result = prime * result + ((lastTimeIn == null) ? 0 : lastTimeIn.hashCode());
		result = prime * result + ((maxLength == null) ? 0 : maxLength.hashCode());
		result = prime * result + ((minLength == null) ? 0 : minLength.hashCode());
		result = prime * result + ((containsUsername == null) ? 0 : containsUsername.hashCode());
		result = prime * result + ((notRepeatHisNumbs == null) ? 0 : notRepeatHisNumbs.hashCode());
		result = prime * result + ((resetAtFirstLogin == null) ? 0 : resetAtFirstLogin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PwdRuleEntity other = (PwdRuleEntity) obj;
		if (contains09 == null) {
			if (other.contains09 != null)
				return false;
		} else if (!contains09.equals(other.contains09))
			return false;
		if (containsAZ == null) {
			if (other.containsAZ != null)
				return false;
		} else if (!containsAZ.equals(other.containsAZ))
			return false;
		if (containsSpecialChars == null) {
			if (other.containsSpecialChars != null)
				return false;
		} else if (!containsSpecialChars.equals(other.containsSpecialChars))
			return false;
		if (containsaz == null) {
			if (other.containsaz != null)
				return false;
		} else if (!containsaz.equals(other.containsaz))
			return false;
		if (defaultPwd == null) {
			if (other.defaultPwd != null)
				return false;
		} else if (!defaultPwd.equals(other.defaultPwd))
			return false;
		if (errorNumbs == null) {
			if (other.errorNumbs != null)
				return false;
		} else if (!errorNumbs.equals(other.errorNumbs))
			return false;
		if (lastTimeIn == null) {
			if (other.lastTimeIn != null)
				return false;
		} else if (!lastTimeIn.equals(other.lastTimeIn))
			return false;
		if (maxLength == null) {
			if (other.maxLength != null)
				return false;
		} else if (!maxLength.equals(other.maxLength))
			return false;
		if (minLength == null) {
			if (other.minLength != null)
				return false;
		} else if (!minLength.equals(other.minLength))
			return false;
		if (containsUsername == null) {
			if (other.containsUsername != null)
				return false;
		} else if (!containsUsername.equals(other.containsUsername))
			return false;
		if (notRepeatHisNumbs == null) {
			if (other.notRepeatHisNumbs != null)
				return false;
		} else if (!notRepeatHisNumbs.equals(other.notRepeatHisNumbs))
			return false;
		if (resetAtFirstLogin == null) {
			if (other.resetAtFirstLogin != null)
				return false;
		} else if (!resetAtFirstLogin.equals(other.resetAtFirstLogin))
			return false;
		return true;
	}

}
