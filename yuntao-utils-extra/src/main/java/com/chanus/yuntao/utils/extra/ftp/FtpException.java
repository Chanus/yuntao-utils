/*
 * Copyright (c) 2020 Chanus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chanus.yuntao.utils.extra.ftp;

import com.chanus.yuntao.utils.core.StringUtils;

/**
 * Ftp 异常
 *
 * @author Chanus
 * @date 2021-03-19 11:23:01
 * @since 1.5.0
 */
public class FtpException extends RuntimeException {
    private static final long serialVersionUID = -4269759687840671084L;

    public FtpException(Throwable e) {
        super(StringUtils.format("{}: {}", e.getClass().getSimpleName(), e.getMessage()), e);
    }

    public FtpException(String message) {
        super(message);
    }

    public FtpException(String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params));
    }

    public FtpException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public FtpException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params), throwable);
    }
}
