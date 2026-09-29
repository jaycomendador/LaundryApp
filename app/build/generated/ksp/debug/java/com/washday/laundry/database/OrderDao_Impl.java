package com.washday.laundry.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class OrderDao_Impl implements OrderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<OrderEntity> __insertionAdapterOfOrderEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateOrderStatus;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOrder;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllOrders;

  public OrderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfOrderEntity = new EntityInsertionAdapter<OrderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `orders` (`id`,`customerName`,`contactNumber`,`serviceName`,`date`,`time`,`weightKg`,`pricePerKg`,`basePrice`,`additionalCharges`,`totalAmount`,`status`,`paymentStatus`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final OrderEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getCustomerName());
        statement.bindString(3, entity.getContactNumber());
        statement.bindString(4, entity.getServiceName());
        statement.bindString(5, entity.getDate());
        statement.bindString(6, entity.getTime());
        statement.bindDouble(7, entity.getWeightKg());
        statement.bindDouble(8, entity.getPricePerKg());
        statement.bindDouble(9, entity.getBasePrice());
        statement.bindDouble(10, entity.getAdditionalCharges());
        statement.bindDouble(11, entity.getTotalAmount());
        statement.bindString(12, entity.getStatus());
        statement.bindString(13, entity.getPaymentStatus());
      }
    };
    this.__preparedStmtOfUpdateOrderStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE orders SET status = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOrder = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM orders WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllOrders = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM orders";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrder(final OrderEntity order, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfOrderEntity.insert(order);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertOrders(final List<OrderEntity> orders,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfOrderEntity.insert(orders);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateOrderStatus(final String orderId, final String newStatus,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateOrderStatus.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, newStatus);
        _argIndex = 2;
        _stmt.bindString(_argIndex, orderId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateOrderStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOrder(final String orderId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOrder.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, orderId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOrder.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllOrders(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllOrders.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllOrders.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<OrderEntity>> getAllOrdersFlow() {
    final String _sql = "SELECT * FROM orders ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"orders"}, new Callable<List<OrderEntity>>() {
      @Override
      @NonNull
      public List<OrderEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerName = CursorUtil.getColumnIndexOrThrow(_cursor, "customerName");
          final int _cursorIndexOfContactNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "contactNumber");
          final int _cursorIndexOfServiceName = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceName");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "weightKg");
          final int _cursorIndexOfPricePerKg = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerKg");
          final int _cursorIndexOfBasePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "basePrice");
          final int _cursorIndexOfAdditionalCharges = CursorUtil.getColumnIndexOrThrow(_cursor, "additionalCharges");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfPaymentStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentStatus");
          final List<OrderEntity> _result = new ArrayList<OrderEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final OrderEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCustomerName;
            _tmpCustomerName = _cursor.getString(_cursorIndexOfCustomerName);
            final String _tmpContactNumber;
            _tmpContactNumber = _cursor.getString(_cursorIndexOfContactNumber);
            final String _tmpServiceName;
            _tmpServiceName = _cursor.getString(_cursorIndexOfServiceName);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpTime;
            _tmpTime = _cursor.getString(_cursorIndexOfTime);
            final double _tmpWeightKg;
            _tmpWeightKg = _cursor.getDouble(_cursorIndexOfWeightKg);
            final double _tmpPricePerKg;
            _tmpPricePerKg = _cursor.getDouble(_cursorIndexOfPricePerKg);
            final double _tmpBasePrice;
            _tmpBasePrice = _cursor.getDouble(_cursorIndexOfBasePrice);
            final double _tmpAdditionalCharges;
            _tmpAdditionalCharges = _cursor.getDouble(_cursorIndexOfAdditionalCharges);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpPaymentStatus;
            _tmpPaymentStatus = _cursor.getString(_cursorIndexOfPaymentStatus);
            _item = new OrderEntity(_tmpId,_tmpCustomerName,_tmpContactNumber,_tmpServiceName,_tmpDate,_tmpTime,_tmpWeightKg,_tmpPricePerKg,_tmpBasePrice,_tmpAdditionalCharges,_tmpTotalAmount,_tmpStatus,_tmpPaymentStatus);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAllOrders(final Continuation<? super List<OrderEntity>> $completion) {
    final String _sql = "SELECT * FROM orders ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<OrderEntity>>() {
      @Override
      @NonNull
      public List<OrderEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerName = CursorUtil.getColumnIndexOrThrow(_cursor, "customerName");
          final int _cursorIndexOfContactNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "contactNumber");
          final int _cursorIndexOfServiceName = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceName");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "weightKg");
          final int _cursorIndexOfPricePerKg = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerKg");
          final int _cursorIndexOfBasePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "basePrice");
          final int _cursorIndexOfAdditionalCharges = CursorUtil.getColumnIndexOrThrow(_cursor, "additionalCharges");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfPaymentStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentStatus");
          final List<OrderEntity> _result = new ArrayList<OrderEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final OrderEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCustomerName;
            _tmpCustomerName = _cursor.getString(_cursorIndexOfCustomerName);
            final String _tmpContactNumber;
            _tmpContactNumber = _cursor.getString(_cursorIndexOfContactNumber);
            final String _tmpServiceName;
            _tmpServiceName = _cursor.getString(_cursorIndexOfServiceName);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpTime;
            _tmpTime = _cursor.getString(_cursorIndexOfTime);
            final double _tmpWeightKg;
            _tmpWeightKg = _cursor.getDouble(_cursorIndexOfWeightKg);
            final double _tmpPricePerKg;
            _tmpPricePerKg = _cursor.getDouble(_cursorIndexOfPricePerKg);
            final double _tmpBasePrice;
            _tmpBasePrice = _cursor.getDouble(_cursorIndexOfBasePrice);
            final double _tmpAdditionalCharges;
            _tmpAdditionalCharges = _cursor.getDouble(_cursorIndexOfAdditionalCharges);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpPaymentStatus;
            _tmpPaymentStatus = _cursor.getString(_cursorIndexOfPaymentStatus);
            _item = new OrderEntity(_tmpId,_tmpCustomerName,_tmpContactNumber,_tmpServiceName,_tmpDate,_tmpTime,_tmpWeightKg,_tmpPricePerKg,_tmpBasePrice,_tmpAdditionalCharges,_tmpTotalAmount,_tmpStatus,_tmpPaymentStatus);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
