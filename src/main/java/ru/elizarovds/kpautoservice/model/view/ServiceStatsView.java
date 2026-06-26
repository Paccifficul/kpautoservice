package ru.elizarovds.kpautoservice.model.view;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "service_stats_view")
@Immutable
public class ServiceStatsView {
    @Id
    private UUID id;
    
    @Column(name = "service_name")
    private String serviceName;
    
    @Column(name = "category_name")
    private String categoryName;
    
    @Column(name = "usage_count")
    private Long usageCount;
    
    @Column(name = "avg_duration")
    private BigDecimal avgDuration;
    
    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public Long getUsageCount() { return usageCount; }
    public void setUsageCount(Long usageCount) { this.usageCount = usageCount; }
    public BigDecimal getAvgDuration() { return avgDuration; }
    public void setAvgDuration(BigDecimal avgDuration) { this.avgDuration = avgDuration; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
}