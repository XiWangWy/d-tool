package com.dmall.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class CellUtil {
	/**
	 * 获取单元格值
	 *
	 * @param cell {@link Cell}单元格
	 * @return 值，类型可能为：Date、Double、Boolean、String
	 * @since 4.6.3
	 */
	public static Object getCellValue(Cell cell) {
		return getCellValue(cell, cell.getCellType());
	}

	/**
	 * 获取单元格值<br>
	 * 如果单元格值为数字格式，则判断其格式中是否有小数部分，无则返回Long类型，否则返回Double类型
	 *
	 * @param cell       {@link Cell}单元格
	 * @param cellType   单元格值类型{@link CellType}枚举，如果为{@code null}默认使用cell的类型
	 * @return 值，类型可能为：Date、Double、Boolean、String
	 */
	public static Object getCellValue(Cell cell, CellType cellType) {
		if (null == cell) {
			return null;
		}
		if (null == cellType) {
			cellType = cell.getCellType();
		}

		Object value;
		switch (cellType) {
			case NUMERIC:
				value = getNumericValue(cell);
				break;
			case BOOLEAN:
				value = cell.getBooleanCellValue();
				break;
//			case FORMULA:
//				// 遇到公式时查找公式结果类型
//				value = getCellValue(cell, cell.getCachedFormulaResultType(), cellEditor);
//				break;
			case BLANK:
				value = "";
				break;
//			case ERROR:
//				final FormulaError error = FormulaError.forInt(cell.getErrorCellValue());
//				value = (null == error) ? StrUtil.EMPTY : error.getString();
//				break;
			default:
				value = cell.getStringCellValue();
		}

		return value;
	}

	/**
	 * 获取数字类型的单元格值
	 *
	 * @param cell 单元格
	 * @return 单元格值，可能为Long、Double、Date
	 */
	private static Object getNumericValue(Cell cell) {
		final double value = cell.getNumericCellValue();

		final CellStyle style = cell.getCellStyle();
		if (null != style) {
			final short formatIndex = style.getDataFormat();
			// 判断是否为日期
			if (isDateType(cell, formatIndex)) {
				return cell.getDateCellValue();
			}

			final String format = style.getDataFormatString();
			// 普通数字
			if (null != format && !format.contains(".")) {
				final long longPart = (long) value;
				if (((double) longPart) == value) {
					// 对于无小数部分的数字类型，转为Long
					return longPart;
				}
			}
		}

		// 某些Excel单元格值为double计算结果，可能导致精度问题，通过转换解决精度问题。
		return Double.parseDouble(NumberToTextConverter.toText(value));
	}

	/**
	 * 是否为日期格式<br>
	 * 判断方式：
	 *
	 * <pre>
	 * 1、指定序号
	 * 2、org.apache.poi.ss.usermodel.DateUtil.isADateFormat方法判定
	 * </pre>
	 *
	 * @param cell        单元格
	 * @param formatIndex 格式序号
	 * @return 是否为日期格式
	 */
	private static boolean isDateType(Cell cell, int formatIndex) {
		// yyyy-MM-dd----- 14
		// yyyy年m月d日---- 31
		// yyyy年m月------- 57
		// m月d日 ---------- 58
		// HH:mm----------- 20
		// h时mm分 -------- 32
		if (formatIndex == 14 || formatIndex == 31 || formatIndex == 57 || formatIndex == 58 || formatIndex == 20 || formatIndex == 32) {
			return true;
		}

		return org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell);
	}

	/**
	 * Object转成指定的类型
	 * @param obj
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static<T> T convert(Object obj, Class<T> type) {
		if (obj != null && StringUtils.isNotBlank(obj.toString())) {
			if (type.equals(Integer.class)||type.equals(int.class)) {
				return (T)new Integer(obj.toString());
			} else if (type.equals(Long.class)||type.equals(long.class)) {
				return (T)new Long(obj.toString());
			} else if (type.equals(Boolean.class)||type.equals(boolean.class)) {
				return (T) new Boolean(obj.toString());
			} else if (type.equals(Short.class)||type.equals(short.class)) {
				return (T) new Short(obj.toString());
			} else if (type.equals(Float.class)||type.equals(float.class)) {
				return (T) new Float(obj.toString());
			} else if (type.equals(Double.class)||type.equals(double.class)) {
				return (T) new Double(obj.toString());
			} else if (type.equals(Byte.class)||type.equals(byte.class)) {
				return (T) new Byte(obj.toString());
			} else if (type.equals(Character.class)||type.equals(char.class)) {
				return (T)new Character(obj.toString().charAt(0));
			} else if (type.equals(String.class)) {
				return (T)(obj.toString());
			} else if (type.equals(BigDecimal.class)) {
				return (T) new BigDecimal(obj.toString());
			} else if (type.equals(LocalDateTime.class)) {
				//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				return (T) LocalDateTime.parse(obj.toString());
			} else if (type.equals(Date.class)) {
				try
				{
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					return (T) formatter.parse(obj.toString());
				}
				catch (ParseException e)
				{
					throw new RuntimeException(e.getMessage());
				}

			}else{
				return null;
			}
		} else {
			if (type.equals(int.class)) {
				return (T)new Integer(0);
			} else if (type.equals(long.class)) {
				return (T)new Long(0L);
			} else if (type.equals(boolean.class)) {
				return (T)new Boolean(false);
			} else if (type.equals(short.class)) {
				return (T)new Short("0");
			} else if (type.equals(float.class)) {
				return (T) new Float(0.0);
			} else if (type.equals(double.class)) {
				return (T) new Double(0.0);
			} else if (type.equals(byte.class)) {
				return (T) new Byte("0");
			} else if (type.equals(char.class)) {
				return (T) new Character('\u0000');
			}else {
				return null;
			}
		}
	}
}