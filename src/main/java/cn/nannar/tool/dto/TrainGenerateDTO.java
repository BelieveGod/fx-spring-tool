package cn.nannar.tool.dto;

import lombok.Data;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author LTJ
 * @date 2023/1/5
 */
@Data
public class TrainGenerateDTO {
    private Boolean useTrainNoFile;

    private String trainNoPattern;

    private Integer carriageNum;

    private Integer ptgNum;

    private Integer trainNumBegin;

    private Integer trainNumEnd;

    private File customTrainNoFile;

    private List<PartTypeDTO> partTypeDTOList;

    @Data
    public static class PartTypeDTO{
        private Integer partType;
        private Map<Integer,String> partNameMap;
    }
}
