# Copy any disabled app files to updated module
if [ -d /data/adb/modules/unlimitedphotos/system ]; then
    ui_print "- Restoring disabled ROM apps configuration"
    cp -arf /data/adb/modules/unlimitedphotos/system $MODPATH
fi

# Copy any supported custom files to updated module
for FILE in custom.app_replace.list custom.fgp.json; do
    if [ -f "/data/adb/modules/unlimitedphotos/$FILE" ]; then
        ui_print "- Restoring $FILE"
        cp -af /data/adb/modules/unlimitedphotos/$FILE $MODPATH/$FILE
    fi
done

# Warn if potentially conflicting modules are installed
for MODULE in Pixelify PixelifyPhotos unlimitedgpics  ; do
    if [ -d /data/adb/modules/$MODULE ]; then
        ui_print "! $MODULE module may cause issues with this module"
    fi
done

# Run common tasks for installation and boot-time
[ -d "$MODPATH/zygisk" ] && . $MODPATH/common_setup.sh