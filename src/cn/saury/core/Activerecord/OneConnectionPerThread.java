/**
 * Copyright (c) 2010-2014, SauryFramework.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.saury.core.Activerecord;

import java.sql.Connection;

import cn.saury.core.ActionInvocation;
import cn.saury.core.AOP.Interceptor;

/**
 * 实现一个线程仅一个数据库连接, 以提高性能
 * 注意是否与事务冲突了
 */
public class OneConnectionPerThread implements Interceptor {
	
	public void intercept(ActionInvocation invocation) {
		Connection conn = null;
		try {
			conn = DbKit.getConnection();
			DbKit.setThreadLocalConnection(conn);
			invocation.invoke();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			DbKit.removeThreadLocalConnection();
			DbKit.close(conn);
		}
	}
}
