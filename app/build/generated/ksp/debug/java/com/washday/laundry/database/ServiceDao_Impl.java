package com.washday.laundry.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
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
public final class ServiceDao_Impl implements ServiceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ServiceEntity> __insertionAdapterOfServiceEntity;

  public ServiceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfServiceEntity = new EntityInsertionAdapter<ServiceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `services` (`id`,`name`,`detail`,`pricePerKg`,`iconName`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ServiceEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        if (entity.getDetail() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDetail());
        }
        statement.bindDouble(4, entity.getPricePerKg());
        statement.bindString(5, entity.getIconName());
      }
    };
  }

  @Override
  public Object insertServices(final List<ServiceEntity> services,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfServiceEntity.insert(services);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertService(final ServiceEntity service,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfServiceEntity.insert(service);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ServiceEntity>> getAllServicesFlow() {
    final String _sql = "SELECT * FROM services";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"services"}, new Callable<List<ServiceEntity>>() {
      @Override
      @NonNull
      public List<ServiceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "detail");
          final int _cursorIndexOfPricePerKg = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerKg");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final List<ServiceEntity> _result = new ArrayList<ServiceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ServiceEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDetail;
            if (_cursor.isNull(_cursorIndexOfDetail)) {
              _tmpDetail = null;
            } else {
              _tmpDetail = _cursor.getString(_cursorIndexOfDetail);
            }
            final double _tmpPricePerKg;
            _tmpPricePerKg = _cursor.getDouble(_cursorIndexOfPricePerKg);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            _item = new ServiceEntity(_tmpId,_tmpName,_tmpDetail,_tmpPricePerKg,_tmpIconName);
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
  public Object getAllServices(final Continuation<? super List<ServiceEntity>> $completion) {
    final String _sql = "SELECT * FROM services";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ServiceEntity>>() {
      @Override
      @NonNull
      public List<ServiceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "detail");
          final int _cursorIndexOfPricePerKg = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerKg");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final List<ServiceEntity> _result = new ArrayList<ServiceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ServiceEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDetail;
            if (_cursor.isNull(_cursorIndexOfDetail)) {
              _tmpDetail = null;
            } else {
              _tmpDetail = _cursor.getString(_cursorIndexOfDetail);
            }
            final double _tmpPricePerKg;
            _tmpPricePerKg = _cursor.getDouble(_cursorIndexOfPricePerKg);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            _item = new ServiceEntity(_tmpId,_tmpName,_tmpDetail,_tmpPricePerKg,_tmpIconName);
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
