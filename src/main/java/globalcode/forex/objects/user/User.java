package globalcode.forex.objects.user;

import globalcode.forex.objects.Selection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor
public class User {

    String name;
    @Nullable
    Selection selection;

}
