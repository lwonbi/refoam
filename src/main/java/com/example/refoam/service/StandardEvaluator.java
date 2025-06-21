package com.example.refoam.service;

import com.example.refoam.domain.ProductLabel;
import com.example.refoam.domain.Standard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StandardEvaluator {
    public ProductLabel evaluate(double injpress, double mold, double fill, double cycle,double plast,double backpress) {
        // 특정 값에 따른 라벨 부여
        if ((fill == 7.228) && (injpress >= 912.541) && (plast >= 3.45 && mold <= 81.281  && cycle <= 75.174)) return ProductLabel.ERR_TEMP;

        if ((fill == 7.124) && (injpress >= 913.324) && (plast >= 3.4 && mold <= 81.281  && cycle <= 75.174)) return ProductLabel.ERR_TEMP;

        if (fill == 6.968 && injpress >= 912.796 && plast >= 3.35 && mold <= 81.28 && cycle <= 75.174) return ProductLabel.ERR_TEMP;

        if (fill == 6.864 && (injpress >= 913.355) && plast >= 3.3 && mold <= 81.367 && cycle <= 75.151) return ProductLabel.ERR_TEMP;


        if (fill == 6.864 && cycle <= 75.069 && injpress >= 920.463) return ProductLabel.ERR_TIME;

        if (fill == 6.864 && cycle <= 74.925 && injpress <= 899.318) return ProductLabel.ERR_TIME;

        if (fill == 6.968 && cycle <= 75.06) return ProductLabel.ERR_TIME;

        if (fill == 7.124 && cycle <= 75.049) return ProductLabel.ERR_TIME;

        if (fill == 7.228 && cycle <= 75.041 && backpress >= 146.898) return ProductLabel.ERR_TIME;

        return ProductLabel.OK;
    }
}
