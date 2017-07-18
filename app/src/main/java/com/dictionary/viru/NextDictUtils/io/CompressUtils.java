package com.dictionary.viru.NextDictUtils.io;


import com.dictionary.viru.NextDictUtils.CLog;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by thuc on 6/30/14.
 */
public class CompressUtils {
    private static final String TAG = CompressUtils.class.getSimpleName();
    private static final int COPY_BUF_SIZE = 8024;
    private static final int SKIP_BUF_SIZE = 4096;

    /**
     * Untar an input file into an output file.
     * <p/>
     * The output file is created in the output folder, having the same name
     * as the input file, minus the '.tar' extension.
     *
     * @param inputFile the input .tar file
     * @param outputDir the output directory file.
     * @return The {@link List} of {@link File}s with the untared content.
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ArchiveException
     */
    public static List<File> unTar(final File inputFile, final File outputDir) throws IOException, ArchiveException {

        CLog.i(TAG, String.format("Untaring %s to dir %s.", inputFile.getAbsolutePath(), outputDir.getAbsolutePath()));

        final List<File> untaredFiles = new LinkedList<File>();
        final InputStream is = new FileInputStream(inputFile);
        final TarArchiveInputStream debInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", is);
        TarArchiveEntry entry = null;
        while ((entry = (TarArchiveEntry) debInputStream.getNextEntry()) != null) {
            final File outputFile = new File(outputDir, entry.getName());
            if (entry.isDirectory()) {
                CLog.i(TAG, String.format("Attempting to write output directory %s.", outputFile.getAbsolutePath()));
                if (!outputFile.exists()) {
                    CLog.i(TAG, String.format("Attempting to create output directory %s.", outputFile.getAbsolutePath()));
                    if (!outputFile.mkdirs()) {
                        throw new IllegalStateException(String.format("Couldn't create directory %s.", outputFile.getAbsolutePath()));
                    }
                }
            } else {
                CLog.i(TAG, String.format("Creating output file %s.", outputFile.getAbsolutePath()));
                final OutputStream outputFileStream = new FileOutputStream(outputFile);
                IOUtils.copy(debInputStream, outputFileStream);
                outputFileStream.close();
            }
            untaredFiles.add(outputFile);
        }
        debInputStream.close();

        return untaredFiles;
    }

    public static List<File> unTar(final File inputFile, final File outputDir, String fileName, SetProgressCallback setProgressCallback) throws IOException, ArchiveException {

        CLog.i(TAG, String.format("Untaring %s to dir %s.", inputFile.getAbsolutePath(), outputDir.getAbsolutePath()));

        final List<File> untaredFiles = new LinkedList<File>();
        final InputStream is = new FileInputStream(inputFile);
        final TarArchiveInputStream debInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", is);
        TarArchiveEntry entry = null;
        while ((entry = (TarArchiveEntry) debInputStream.getNextEntry()) != null) {
            CLog.d(TAG, "entry name: " + entry.getName() + " " + fileName);
            final File outputFile = new File(outputDir, fileName + ".ndf");
            if (entry.isDirectory()) {
                CLog.i(TAG, String.format("Attempting to write output directory %s.", outputFile.getAbsolutePath()));
                if (!outputFile.exists()) {
                    CLog.i(TAG, String.format("Attempting to create output directory %s.", outputFile.getAbsolutePath()));
                    if (!outputFile.mkdirs()) {
                        throw new IllegalStateException(String.format("Couldn't create directory %s.", outputFile.getAbsolutePath()));
                    }
                }
            } else {
                CLog.i(TAG, String.format("Creating output file %s.", outputFile.getAbsolutePath()));
                final OutputStream outputFileStream = new FileOutputStream(outputFile);
//                IOUtils.copy(debInputStream, outputFileStream);
                int buffersize = COPY_BUF_SIZE;
                final byte[] buffer = new byte[buffersize];
                int n = 0;
                long count = 0;
                while (-1 != (n = debInputStream.read(buffer))) {
                    outputFileStream.write(buffer, 0, n);
                    count += n;
                    setProgressCallback.setProgress(count);
                }
                outputFileStream.close();
            }
            untaredFiles.add(outputFile);
        }
        debInputStream.close();

        return untaredFiles;
    }

    /**
     * Ungzip an input file into an output file.
     * <p/>
     * The output file is created in the output folder, having the same name
     * as the input file, minus the '.gz' extension.
     *
     * @param inputFile the input .gz file
     * @param outputDir the output directory file.
     * @return The {@File} with the ungzipped content.
     * @throws IOException
     */
    public static File unGzip(final File inputFile, final File outputDir, SetProgressCallback setProgressCallback) throws IOException {

        CLog.i(TAG, String.format("Ungzipping %s to dir %s.", inputFile.getAbsolutePath(), outputDir.getAbsolutePath()));

        final File outputFile = new File(outputDir, inputFile.getName().substring(0, inputFile.getName().length() - 3));

        final GZIPInputStream in = new GZIPInputStream(new FileInputStream(inputFile));
        final FileOutputStream out = new FileOutputStream(outputFile);

//        long bytes = IOUtils.copy(in, out);
        int buffersize = COPY_BUF_SIZE;
        final byte[] buffer = new byte[buffersize];
        int n = 0;
        long count = 0;
        while (-1 != (n = in.read(buffer))) {
            out.write(buffer, 0, n);
            count += n;
            CLog.d(TAG, "File Written with " + count + " bytes");
            setProgressCallback.setProgress(count);
        }


        in.close();
        out.close();

        return outputFile;
    }

    public interface SetProgressCallback {
        void setProgress(long progress);
    }

    /**
     * Unbzip2 an input file into an output file.
     * <p/>
     * The output file is created in the output folder, having the same name
     * as the input file, minus the '.bz2' extension.
     *
     * @param inputFile the input .bz2 file
     * @param outputDir the output directory file.
     * @return The {@File} with the unbzip2ed content.
     * @throws IOException
     */
    public static File unBzip2(final File inputFile, final File outputDir) throws IOException {

        CLog.i(TAG, String.format("Unbzip2ing %s to dir %s.", inputFile.getAbsolutePath(), outputDir.getAbsolutePath()));

        final File outputFile = new File(outputDir, inputFile.getName().substring(0, inputFile.getName().length() - 3));

        final BZip2CompressorInputStream in = new BZip2CompressorInputStream(new FileInputStream(inputFile));
        final FileOutputStream out = new FileOutputStream(outputFile);

        long bytes = IOUtils.copy(in, out);
        CLog.d(TAG, "File Written with " + bytes + " bytes");
        in.close();
        out.close();

        return outputFile;
    }
}
