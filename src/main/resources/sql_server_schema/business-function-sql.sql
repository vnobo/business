IF EXISTS(SELECT *
          FROM sysobjects
          WHERE id = object_id('dbo.ST_Purchase') AND sysstat & 0xf = 4)
  DROP PROCEDURE [dbo].[ST_Purchase]
GO
CREATE PROCEDURE [dbo].[ST_Purchase](@SheetID VARCHAR(20), @Checker VARCHAR(10))
AS
  -------------------------------------------------------------------
  --创建者	：李君
  --创建时间	：2015-04-02
  --说明		：订单审核过程	断点号： 150413nn
  --数据流说明	：
  --修改记录流水	：
  -------------------------------------------------------------------
  BEGIN
    DECLARE @Err         INT;
    DECLARE @BreakPoint  INT;
    DECLARE @Msg         VARCHAR(255);
    DECLARE @RowCount    INT;
    DECLARE @StartWork   INT;

    --
    DECLARE @CurrentFlag INT;
    DECLARE @NextFlag    INT;

    BEGIN TRAN
    SELECT @StartWork = 1;

    SELECT @BreakPoint = 15041301;
    SELECT @CurrentFlag = flag
    FROM Purchase
    WHERE SheetID = @SheetID;
    SELECT @Err = @@Error;
    IF @Err <> 0 OR @Err IS NULL OR @@error != 0 OR @CurrentFlag IS NULL
      BEGIN
        SELECT @Msg = '取单据状态失败!';
        GOTO ErrHandle;
      END;

    SELECT @BreakPoint = 15041305;
    SELECT @NextFlag = NextFlag
    FROM Worklist
    WHERE CurrentFlag = @CurrentFlag;
    SELECT @Err = @@Error;
    IF @Err <> 0 OR @Err IS NULL OR @@error != 0 OR @NextFlag IS NULL OR @NextFlag = 0
      BEGIN
        SELECT @Msg = '取单据状态工作流失败!';
        GOTO ErrHandle;
      END;

    SELECT @BreakPoint = 15041310;
    UPDATE Purchase
    SET Flag = @NextFlag
    WHERE SheetID = @SheetID AND Flag = @CurrentFlag;
    SELECT
        @Err = @@error,
        @RowCount = @@RowCount;
    IF @Err != 0
      GOTO ErrHandle;
    IF @RowCount = 0
      BEGIN
        SELECT @Msg = '单据已经不存在!';
        GOTO ErrHandle;
      END;

    IF @NextFlag = 1
      BEGIN
        UPDATE Purchaseitem
        SET valqty = qty,sendqty = qty
        WHERE SheetID = @SheetID;
        SELECT
            @Err = @@error,
            @RowCount = @@RowCount;
        IF @Err != 0
          GOTO ErrHandle;
        IF @RowCount = 0
          BEGIN
            SELECT @Msg = '单据已经不存在!';
            GOTO ErrHandle;
          END;
      END;



    IF @NextFlag = 2
      BEGIN
        UPDATE Purchase
        SET Hander = @Checker, HandDate = getdate()
        WHERE SheetID = @SheetID;
        SELECT
            @Err = @@error,
            @RowCount = @@RowCount;
        IF @Err != 0
          GOTO ErrHandle;
        IF @RowCount = 0
          BEGIN
            SELECT @Msg = '单据已经不存在!';
            GOTO ErrHandle;
          END;
      END;

    IF @NextFlag = 3
      BEGIN
        UPDATE Purchaseitem
        SET valqty = sendqty
        WHERE SheetID = @SheetID;
        SELECT
            @Err = @@error,
            @RowCount = @@RowCount;
        IF @Err != 0
          GOTO ErrHandle;
        IF @RowCount = 0
          BEGIN
            SELECT @Msg = '单据已经不存在!';
            GOTO ErrHandle;
          END;
      END;

    IF @NextFlag = 100
      BEGIN
        UPDATE Purchase
        SET Checker = @Checker, CheckDate = getdate()
        WHERE SheetID = @SheetID;
        SELECT
            @Err = @@error,
            @RowCount = @@RowCount;
        IF @Err != 0
          GOTO ErrHandle;
        IF @RowCount = 0
          BEGIN
            SELECT @Msg = '单据已经不存在!'
            GOTO ErrHandle;
          END;
      END;

    COMMIT TRAN
    SELECT @StartWork = 0;

    RETURN 0;

    ErrHandle:
    IF @@error != 0
      SELECT @Err = @@error
    IF @@TRANCOUNT > 0
      ROLLBACK TRANSACTION  --内部事务操作必须
    RAISERROR ('%s,断点=%d,Err=%d', 16, 1, @Msg, @BreakPoint, @Err);
    RETURN -1;
  END
GO


IF EXISTS(SELECT *
          FROM sysobjects
          WHERE id = object_id('dbo.ST_PurchaseCancel') AND sysstat & 0xf = 4)
  DROP PROCEDURE [dbo].[ST_PurchaseCancel]
GO
CREATE PROCEDURE [dbo].[ST_PurchaseCancel](@SheetID VARCHAR(20), @Checker VARCHAR(10))
AS
  -------------------------------------------------------------------
  --创建者	：李君
  --创建时间	：2015-04-02
  --说明		：订单取消过程	断点号： 150413nn
  --数据流说明	：
  --修改记录流水	：
  -------------------------------------------------------------------
  BEGIN
    DECLARE @Err        INT;
    DECLARE @BreakPoint INT;
    DECLARE @Msg        VARCHAR(255);
    DECLARE @RowCount   INT;
    DECLARE @StartWork  INT;

    BEGIN TRAN
    SELECT @StartWork = 1;


    SELECT @BreakPoint = 15041310;
    UPDATE Purchase
    SET Flag = 99, Checker = @Checker, CheckDate = getdate()
    WHERE SheetID = @SheetID AND FLAG IN (1,2,3);
    SELECT
        @Err = @@error,
        @RowCount = @@RowCount;
    IF @Err != 0
      GOTO ErrHandle;
    IF @RowCount = 0
      BEGIN
        SELECT @Msg = '单据已经不存在!';
        GOTO ErrHandle;
      END;

    COMMIT TRAN
    SELECT @StartWork = 0;

    RETURN 0;

    ErrHandle:
    IF @@error != 0
      SELECT @Err = @@error
    IF @@TRANCOUNT > 0
      ROLLBACK TRANSACTION  --内部事务操作必须
    RAISERROR ('%s,断点=%d,Err=%d', 16, 1, @Msg, @BreakPoint, @Err);
    RETURN -1;
  END
GO
