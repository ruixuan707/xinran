package com.monco.core.page;

import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * PageResult
 *
 * @author Monco
 * @version 1.0.0
 */
@Data
public class PageResult {

    /** pageable 对象 */
    private Pageable pageable;
    /** content 对象 */
    private Object content;
    /** 总条数 对象 */
    private Long totalElements;
    /** 统计数字 */
    private Map<String, Object> map;

    public PageResult(Pageable pageable, Object content, Long totalElements) {
        this.pageable = pageable;
        this.content = content;
        this.totalElements = totalElements;
    }
}