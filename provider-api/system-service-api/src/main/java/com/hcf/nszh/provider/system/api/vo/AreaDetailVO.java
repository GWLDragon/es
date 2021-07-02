package com.hcf.nszh.provider.system.api.vo;

import com.hcf.nszh.common.persistence.AbstractTreeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Furant
 * 2019/7/22 10:59
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AreaDetailVO extends AbstractTreeEntity<AreaDetailVO> {

    @ApiModelProperty("区域编码")
    private String code;
    @ApiModelProperty("区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）")
    private String type;
}
