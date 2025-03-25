package online.shenjian.cloud.api.base.model.es;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "doge")
@Data
public class Doge {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String address;

    @Field(type = FieldType.Keyword)
    private String wallet;

    @Field(type = FieldType.Integer)
    private Integer rank;

    @Field(type = FieldType.Long)
    private Long balance;

    @Field(type = FieldType.Float)
    private Float percent;

    @Field(type = FieldType.Date, format = DateFormat.date)
    private String c_date;
}

