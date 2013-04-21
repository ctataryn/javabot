package javabot.model.criteria;

import com.antwerkz.critter.TypeSafeFieldEnd;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.query.Criteria;
import com.google.code.morphia.query.CriteriaContainer;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.QueryImpl;

public class OperationEventCriteria {
  private Query<javabot.model.OperationEvent> query;
  private Datastore ds;

  public Query<javabot.model.OperationEvent> query() {
    return query;
  }

  public OperationEventCriteria(Datastore ds) {
    this.ds = ds;
    query = ds.find(javabot.model.OperationEvent.class);
  }

  public CriteriaContainer or(Criteria... criteria) {
    return query.or(criteria);
  }

  public CriteriaContainer and(Criteria... criteria) {
    return query.and(criteria);
  }

  public TypeSafeFieldEnd<? extends CriteriaContainer, javabot.model.OperationEvent, java.util.Date> completed() {
    return new TypeSafeFieldEnd<>(query, query.criteria("completed"));
  }

  public OperationEventCriteria distinctCompleted() {
    ((QueryImpl) query).getCollection().distinct("completed");
    return this;
  }

  public TypeSafeFieldEnd<? extends CriteriaContainer, javabot.model.OperationEvent, org.bson.types.ObjectId> id() {
    return new TypeSafeFieldEnd<>(query, query.criteria("id"));
  }

  public OperationEventCriteria distinctId() {
    ((QueryImpl) query).getCollection().distinct("id");
    return this;
  }

  public TypeSafeFieldEnd<? extends CriteriaContainer, javabot.model.OperationEvent, java.lang.String> operation() {
    return new TypeSafeFieldEnd<>(query, query.criteria("operation"));
  }

  public OperationEventCriteria distinctOperation() {
    ((QueryImpl) query).getCollection().distinct("operation");
    return this;
  }

  public TypeSafeFieldEnd<? extends CriteriaContainer, javabot.model.OperationEvent, java.lang.String> requestedBy() {
    return new TypeSafeFieldEnd<>(query, query.criteria("requestedBy"));
  }

  public OperationEventCriteria distinctRequestedBy() {
    ((QueryImpl) query).getCollection().distinct("requestedBy");
    return this;
  }

  public TypeSafeFieldEnd<? extends CriteriaContainer, javabot.model.OperationEvent, java.util.Date> requestedOn() {
    return new TypeSafeFieldEnd<>(query, query.criteria("requestedOn"));
  }

  public OperationEventCriteria distinctRequestedOn() {
    ((QueryImpl) query).getCollection().distinct("requestedOn");
    return this;
  }

  public TypeSafeFieldEnd<? extends CriteriaContainer, javabot.model.OperationEvent, javabot.model.AdminEvent.State> state() {
    return new TypeSafeFieldEnd<>(query, query.criteria("state"));
  }

  public OperationEventCriteria distinctState() {
    ((QueryImpl) query).getCollection().distinct("state");
    return this;
  }

  public TypeSafeFieldEnd<? extends CriteriaContainer, javabot.model.OperationEvent, javabot.model.EventType> type() {
    return new TypeSafeFieldEnd<>(query, query.criteria("type"));
  }

  public OperationEventCriteria distinctType() {
    ((QueryImpl) query).getCollection().distinct("type");
    return this;
  }
}
