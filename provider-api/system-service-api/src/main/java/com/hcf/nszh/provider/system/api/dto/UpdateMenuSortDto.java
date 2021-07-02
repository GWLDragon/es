package com.hcf.nszh.provider.system.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Furant 2019/7/ 15:15
 */
@Data
public class UpdateMenuSortDto implements Serializable {

	private String menuId;
	private BigDecimal sort;
}
