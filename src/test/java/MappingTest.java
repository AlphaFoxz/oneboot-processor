import com.github.alphafoxz.oneboot_processor.annotations.OnebootMapper;
import com.github.alphafoxz.oneboot_processor.annotations.OnebootMapping;

import java.util.List;
import java.util.Map;

@OnebootMapper
public abstract class MappingTest {
    @OnebootMapping(targets = {
            Map.class,
            List.class
    })
    Map<String, String> map;
}
